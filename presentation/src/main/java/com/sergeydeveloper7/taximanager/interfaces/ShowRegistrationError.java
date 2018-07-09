package com.sergeydeveloper7.taximanager.interfaces;

import com.sergeydeveloper7.data.enums.ValidationError;

/**
 * Created by serg on 22.03.18.
 */

public interface ShowRegistrationError {
    void showError(ValidationError validationError);
}
