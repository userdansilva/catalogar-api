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
    private final String azureCustomUrl;
    private final BlobServiceClient blobServiceClient;
    private final String containerName;

    public StorageService(
            @Value("${azure.storage.custom-url}") String azureCustomUrl,
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName
    ) {
        this.azureCustomUrl = azureCustomUrl;
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

        String sasToken = blockBlobClient.generateSas(sasSignatureValues);
        String storageAccountUrl = blobServiceClient.getAccountUrl();

        String defaultAccessUrl = blockBlobClient.getBlobUrl();
        String customAccessUrl = defaultAccessUrl.replace(
                storageAccountUrl,
                azureCustomUrl
        );

        // Upload must be done at default blob url
        String uploadUrl = defaultAccessUrl + "?" + sasToken;

        return new Storage(fileName, uploadUrl, customAccessUrl);
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
        return azureCustomUrl + "/" + containerName + "/";
    }
}
