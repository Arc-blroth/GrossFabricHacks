package net.devtech.grossfabrichacks.util;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Debug {
    private static final Logger LOGGER = LogManager.getLogger("GrossFabricHacks/test");

    public static void printFields(final Class<?> klass, final Object object) {
        Arrays.stream(klass.getDeclaredFields()).forEach(field -> {
            try {
                final Object value = field.get(object);
                final String message = String.format("%s = %s", field, value != null && value.getClass().isArray() ? Arrays.deepToString((Object[]) value) : value);

                for (final String line : message.split("\n")) {
                    LOGGER.info(line);
                }
            } catch (final IllegalAccessException exception) {
                System.exit(768);
            }
        });
    }

    public static void listR(final URL resource) {
        listR(resource.getFile());
    }

    public static void listR(final String file) {
        listR(new File(file));
    }

    public static void listR(final File file) {
        listR(file, 0);
    }

    public static void listR(final File file, final int level) {
        final StringBuilder output = new StringBuilder();

        for (int i = 0; i < level; i++) {
            output.append("    ");
        }

        if (file.isFile()) {
            output.append(file);

            LOGGER.error(output);
        } else {
            if (level == 0) {
                output.append(file);
            } else {
                output.append(file.getName().substring(file.getName().lastIndexOf('/') + 1));
            }

            LOGGER.warn(output);

            for (final File feil : file.listFiles()) {
                listR(feil, level + 1);
            }
        }
    }
}
