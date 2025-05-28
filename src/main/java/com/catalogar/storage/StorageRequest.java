package com.catalogar.storage;

import com.catalogar.common.validation.FileType;
import jakarta.validation.constraints.NotEmpty;

public record StorageRequest(
        @NotEmpty(message = "Tipo de arquivo obrigatório")
        @FileType(message = "Tipo de arquivo permitido: WEBP, JPG, PNG ou SVG")
        String fileType
) {
}
