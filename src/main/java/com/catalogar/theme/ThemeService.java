package com.catalogar.theme;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.BadRequestException;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.image.Image;
import com.catalogar.image.ImageRequest;
import com.catalogar.storage.StorageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final UserService userService;
    private final MessageService messageService;
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;
    private final StorageService storageService;

    public ThemeService(UserService userService, MessageService messageService, ThemeRepository themeRepository, ThemeMapper themeMapper, StorageService storageService) {
        this.userService = userService;
        this.messageService = messageService;
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
        this.storageService = storageService;
    }

    public Theme create(ThemeRequest request, User user) {
        Catalog currentCatalog = getUserCurrentCatalog(user);
        boolean hasTheme = currentCatalog.hasTheme();

        if (hasTheme) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.theme.already_created")
            );
        }

        return create(request, currentCatalog);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    private Theme create(ThemeRequest request, Catalog catalog) {
        Theme theme = themeMapper.toTheme(request);
        theme.setCatalog(catalog);

        theme.setLogo(request.logo() != null
                ? toLogo(request.logo(), theme, catalog)
                : null);

        return themeRepository.save(theme);
    }

    private Image toLogo(ImageRequest request, Theme theme, Catalog catalog) {
        String blobUrl = storageService.getBlobUrl();
        String url = request.url();

        if (!url.startsWith(blobUrl)) {
            throw new BadRequestException(
                    messageService.getMessage("error.image.invalid_url")
            );
        }

        String name = request.fileName();
        Long size = (long) request.sizeInBytes();
        Short width = (short) request.width();
        Short height = (short) request.height();
        String altText = request.altText();

        return new Image(name, url, size, catalog, width, height, altText);
    }

    public Theme update(ThemeRequest request, User user) {
        Catalog currentCatalog = getUserCurrentCatalog(user);
        boolean hasTheme = currentCatalog.hasTheme();

        if (!hasTheme) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.theme.not_found")
            );
        }

        Theme theme = currentCatalog.getTheme();

        return update(request, theme, currentCatalog);
    }

    private Theme update(ThemeRequest request, Theme theme, Catalog catalog) {
        theme.setPrimaryColor(request.primaryColor());
        theme.setSecondaryColor(request.secondaryColor());

        theme.setLogo(request.logo() != null
                ? toLogo(request.logo(), theme, catalog)
                : null);

        return themeRepository.save(theme);
    }
}
