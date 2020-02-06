package net.seesharpsoft.intellij.plugins.csv;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.resolve.FileContextUtil;
import net.seesharpsoft.intellij.plugins.csv.psi.CsvTypes;

import java.util.regex.Pattern;

public final class CsvParserUtil {

    public static final Pattern ESCAPE_TEXT_PATTERN = Pattern.compile("[,;|\\t\\r\\n]");

    private CsvParserUtil() {
        // static utility class
    }

    public static boolean separator(PsiBuilder builder, int tokenType) {
        if (builder.getTokenType() == CsvTypes.COMMA) {
            PsiFile psiFile = builder.getUserDataUnprotected(FileContextUtil.CONTAINING_FILE_KEY);
            if (psiFile == null) {
                throw new UnsupportedOperationException("parser requires containing file");
            }
            return CsvHelper.getCurrentValueSeparator(psiFile).isValueSeparator(builder.getTokenText());
        }
        return false;
    }

    public static boolean escapeCharacter(PsiBuilder builder, int tokenType) {
        if (builder.getTokenType() == CsvTypes.ESCAPED_TEXT) {
            PsiFile psiFile = builder.getUserDataUnprotected(FileContextUtil.CONTAINING_FILE_KEY);
            if (psiFile == null) {
                throw new UnsupportedOperationException("parser requires containing file");
            }
            String tokenText = builder.getTokenText();
            return CsvHelper.getCurrentEscapeCharacter(psiFile).isEscapedQuote(tokenText) ||
                    ESCAPE_TEXT_PATTERN.matcher(tokenText).matches();
        }
        return false;
    }

}
