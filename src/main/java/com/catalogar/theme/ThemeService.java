package com.catalogar.theme;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final UserService userService;
    private final MessageService messageService;
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public ThemeService(UserService userService, MessageService messageService, ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.userService = userService;
        this.messageService = messageService;
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
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

        return themeRepository.save(theme);
    }

    public Theme update(ThemeRequest request, User user) {
        Catalog currentCatalog = getUserCurrentCatalog(user);
        boolean hasTheme = currentCatalog.hasTheme();

        if (!hasTheme) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.theme.not_found")
            );
        }

        return update(request, currentCatalog.getTheme());
    }

    private Theme update(ThemeRequest request, Theme theme) {
        theme.setPrimaryColor(request.primaryColor());
        theme.setSecondaryColor(request.secondaryColor());
        theme.setLogoUrl(request.logoUrl());

        return themeRepository.save(theme);
    }
}
