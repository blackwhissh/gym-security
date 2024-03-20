package com.epam.hibernate;

import com.epam.hibernate.entity.RoleEnum;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.exception.NoAuthorityException;
import com.epam.hibernate.repository.UserRepository;

import java.util.Random;
import java.util.logging.Logger;

public class Utils {
    private final static String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Logger logger = Logger.getLogger(Utils.class.getName());
    private static int serialNumber = 0;
    private static StringBuilder sb;

    public static String generateUsername(String firstName, String lastName, boolean exists) {
        sb = new StringBuilder();
        logger.info("Generating Username");
        sb.append(firstName).append(".").append(lastName);
        if (exists) {
            logger.info("Appending Serial Number to the Username");
            sb.append(serialNumber);
            serialNumber++;
        }
        return sb.toString();
    }

    public static String generatePassword() {
        Random random = new Random();
        sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static void checkAdmin(String username, UserRepository repository) {
        User user = repository.findByUsername(username);
        if (user.getRole() != RoleEnum.ADMIN) {
            logger.warning("User " + username + " does not have admin authority.");
            throw new NoAuthorityException("User must be an admin");
        }
    }
}
