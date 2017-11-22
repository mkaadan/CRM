package com.cylinder.crmusers.model.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;

import javax.validation.constraints.Pattern;
import java.util.Optional;

/**
 * @author Ryan Piper
 * A form object used to update a crm user's password.
 */
public class PasswordForm {

    /**
     * The current password of the user.
     *
     * @param currentPassword the password the user is currently using.
     * @return the current password of the user.
     */
    @Getter
    @Setter
    protected String currentPassword;

    /**
     * The id of the account that will have its password altered.
     *
     * @param accountId the new value to be set as the account id.
     * @return the account id.
     */
    @Getter
    @Setter
    protected Long accountId;

    /**
     * The new password the user wishes to change to.
     *
     * @param newPassword the new password the user wishes to change to.
     * @return the new password.
     */
    @Getter
    @Setter
    @Pattern(regexp = ".{8,}", message = "Your new password should be at least 8 characters long.")
    protected String newPassword;

    public PasswordForm() {
    }

    /**
     * Hash the new password for storge in the database.
     *
     * @param passwordEncoder the bcrypt password encoder that hashes the password.
     */
    public void hashNewPassword(BCryptPasswordEncoder passwordEncoder) {
        this.newPassword = passwordEncoder.encode(this.newPassword);
    }

    /**
     * Check if the user submitted password matches what is in the database.
     *
     * @param passwordEncoder the bcrypt password encoder that compares the password
     * @param currentPassword the current password for the user as stored in the database.
     * @param fieldName       the associated field name for the field error.
     * @return the error if one exists.
     */
    public Optional<FieldError> isUserSubPasswordValid(BCryptPasswordEncoder passwordEncoder,
                                                       String currentPassword,
                                                       String fieldName) {
        if (passwordEncoder.matches(this.currentPassword, currentPassword)) {
            return Optional.empty();
        } else {
            return Optional.of(new FieldError("String",
                    fieldName,
                    "Invalid password was entered."));
        }
    }
}
