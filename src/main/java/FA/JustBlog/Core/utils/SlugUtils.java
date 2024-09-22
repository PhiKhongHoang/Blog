package FA.JustBlog.Core.utils;

import java.text.Normalizer;

public class SlugUtils {
    public static String toSlug(String input) {
        String nowhitespace = input.trim().replaceAll("\\s+", "-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("[^\\w-]", "");
        return slug.toLowerCase();
    }
}
