package com.catalogar.theme;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.logo.Logo;
import com.catalogar.logo.LogoRequest;
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

        if (request.logo() != null) {
            Logo logo = toLogo(request.logo(), theme);
            theme.setLogo(logo);
        }

        return themeRepository.save(theme);
    }

    private Logo toLogo(LogoRequest request, Theme theme) {
        String blobUrl = storageService.getBlobUrl();

        String name = request.name();
        String url = blobUrl + name;
        Short width = (short) request.width();
        Short height = (short) request.height();

        return new Logo(name, url, width, height, theme);
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

        theme.setLogo(request.logo() != null
                ? toLogo(request.logo(), theme)
                : null);

        return update(request, theme);
    }

    private Theme update(ThemeRequest request, Theme theme) {
        theme.setPrimaryColor(request.primaryColor());
        theme.setSecondaryColor(request.secondaryColor());

        return themeRepository.save(theme);
    }
}
