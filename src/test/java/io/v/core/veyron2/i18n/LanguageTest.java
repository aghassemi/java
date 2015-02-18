package io.v.core.veyron2.i18n;

import android.test.AndroidTestCase;

import io.v.core.veyron2.android.V;
import io.v.core.veyron2.context.VContext;
import io.v.core.veyron2.context.VContextImpl;

/**
 * Tests for the various Language utility methods.
 */
public class LanguageTest extends AndroidTestCase {
    static {
        V.init();
    }

    public void testLanguageFromContext() {
        final VContext dcWithoutLang = VContextImpl.create();
        final VContext dcWithEN = Language.contextWithLanguage(dcWithoutLang, "en");
        final VContext dcWithFR = Language.contextWithLanguage(dcWithEN, "fr");
        String got = "";

        got = Language.languageFromContext(dcWithoutLang);
        if (!got.isEmpty()) {
            fail(String.format("languageFromContext(dcWithoutLangID); got %v, want \"\"", got));
        }

        got = Language.languageFromContext(dcWithEN);
        if (!got.equals("en")) {
            fail(String.format("Language.languageFromContext(dcWithEN); got %v, want \"en\"", got));
        }

        got = Language.languageFromContext(dcWithFR);
        if (!got.equals("fr")) {
            fail(String.format("Language.languageFromContext(dcWithFR); got %v, want \"fr\"", got));
        }
    }

    public void testBaseLanguage() {
        expectBaseLanguage("en", "en");
        expectBaseLanguage("en-US", "en");
    }

    private void expectBaseLanguage(String lang, String want) {
        final String got = Language.baseLanguage(lang);
        if (!got.equals(want)) {
            fail(String.format("baseLanguage(%s) got %s, want %s", lang, got, want));
        }
    }
}
