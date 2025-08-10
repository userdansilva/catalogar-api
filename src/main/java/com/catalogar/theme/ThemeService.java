package com.catalogar.theme;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.image.ImageMapper;
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
    private final ImageMapper imageMapper;

    public ThemeService(UserService userService, MessageService messageService, ThemeRepository themeRepository, ThemeMapper themeMapper, StorageService storageService, ImageMapper imageMapper) {
        this.userService = userService;
        this.messageService = messageService;
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
        this.storageService = storageService;
        this.imageMapper = imageMapper;
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
        ImageRequest logoRequest = request.logo();

        Theme theme = themeMapper.toTheme(request);

        if (logoRequest != null) {
            validateImage(request.logo());

            theme.setLogo(imageMapper.toImage(
                    request.logo(), catalog));
        }

        theme.setCatalog(catalog);

        return themeRepository.save(theme);
    }

    private void validateImage(ImageRequest request) {
        String imageUrl = request.url();;

        storageService.validateUrl(imageUrl);
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
        ImageRequest logoRequest = request.logo();

        if (logoRequest != null) {
            validateImage(request.logo());

            boolean isNewImage = !theme.getLogo().getUrl()
                    .equals(logoRequest.url());

            if (isNewImage) {
                theme.setLogo(imageMapper.toImage(
                        logoRequest, catalog));
            }
        } else {
            theme.setLogo(null);
        }

        theme.setPrimaryColor(request.primaryColor());
        theme.setSecondaryColor(request.secondaryColor());

        return themeRepository.save(theme);
    }
}
