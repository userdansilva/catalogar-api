package com.catalogar.image;

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
public class ImageService {
    private final BlobServiceClient blobServiceClient;
    private final String containerName;
    private final ImageRepository imageRepository;
    private final MessageService messageService;

    public ImageService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName, ImageRepository imageRepository, MessageService messageService
    ) {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        this.containerName = containerName;
        this.imageRepository = imageRepository;
        this.messageService = messageService;
    }

    public String generateSasToken(String fileName) {
        validateFileName(fileName);

        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String uniqueFileName = generateUniqueImageName(fileExtension);

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName)
                .getBlobClient(uniqueFileName);
        BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();

        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true)
                .setWritePermission(true);

        OffsetDateTime expiryTime = OffsetDateTime.now().plusMinutes(5);

        var sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setProtocol(SasProtocol.HTTPS_ONLY);

        String sasToken = blockBlobClient.generateSas(sasSignatureValues);

        return blockBlobClient.getBlobUrl() + "?" + sasToken;
    }

    private void validateFileName(String fileName) {
        if (!fileName.contains(".")) {
            throw new BadRequestException(
                    messageService.getMessage("error.image.invalid_filename")
            );
        }
    }

    private String generateUniqueImageName(String fileExtension) {
        UUID id;

        do {
            id  = UUID.randomUUID();
        } while (existsById(id));

        return id.toString() + fileExtension;
    }

    private boolean existsById(UUID id) {
        return imageRepository.existsById(id);
    }
}
