package com.catalogar.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.sas.SasProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class StorageService {
    private final BlobServiceClient blobServiceClient;
    private final String containerName;

    public StorageService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName
    ) {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        this.containerName = containerName;
    }

    public Storage generateSasToken(StorageRequest request) {
        FileType fileType = FileType.valueOf(request.fileType());

        String fileExtension = getFileExtension(fileType);
        String fileName = UUID.randomUUID() + fileExtension;

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName)
                .getBlobClient(fileName);
        BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();

        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true)
                .setWritePermission(true);

        OffsetDateTime expiryTime = OffsetDateTime.now().plusMinutes(5);

        var sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setProtocol(SasProtocol.HTTPS_ONLY);

        String accessUrl = blockBlobClient.getBlobUrl();
        String uploadUrl = accessUrl + "?" + blockBlobClient.generateSas(sasSignatureValues);

        return new Storage(fileName, uploadUrl, accessUrl);
    }
    
    public String getFileExtension(FileType fileType) {
        return switch (fileType) {
            case JPG -> ".jpg";
            case PNG -> ".png";
            case SVG -> ".svg";

            default -> ".webp";
        };
    }

    public String getBlobUrl() {
        return blobServiceClient.getAccountUrl() + "/" + containerName + "/";
    }
}
