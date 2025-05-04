package com.catalogar.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.sas.SasProtocol;
import com.catalogar.common.exception.BadRequestException;
import com.catalogar.common.message.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class StorageService {
    private final BlobServiceClient blobServiceClient;
    private final String containerName;
    private final MessageService messageService;

    public StorageService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName,
            MessageService messageService
    ) {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        this.containerName = containerName;
        this.messageService = messageService;
    }

    public Storage generateSasToken(String fileName) {
        validateFileName(fileName);

        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + fileExtension;

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName)
                .getBlobClient(newFileName);
        BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();

        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true)
                .setWritePermission(true);

        OffsetDateTime expiryTime = OffsetDateTime.now().plusMinutes(5);

        var sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setProtocol(SasProtocol.HTTPS_ONLY);

        String imageUrl = blockBlobClient.getBlobUrl();
        String sasToken = imageUrl + "?" + blockBlobClient.generateSas(sasSignatureValues);

        return new Storage(
                sasToken,
                newFileName,
                imageUrl);
    }

    private void validateFileName(String fileName) {
        if (!fileName.contains(".")) {
            throw new BadRequestException(
                    messageService.getMessage("error.image.invalid_filename")
            );
        }
    }

    public String getBlobUrl() {
        return blobServiceClient.getAccountUrl() + "/" + containerName + "/";
    }
}
