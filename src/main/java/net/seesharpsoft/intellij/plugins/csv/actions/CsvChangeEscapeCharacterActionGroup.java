package net.seesharpsoft.intellij.plugins.csv.actions;

import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiFile;
import net.seesharpsoft.intellij.plugins.csv.CsvLanguage;
import net.seesharpsoft.intellij.plugins.csv.components.CsvFileAttributes;
import net.seesharpsoft.intellij.plugins.csv.settings.CsvEditorSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CsvChangeEscapeCharacterActionGroup extends ActionGroup {

    private static final AnAction[] CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS;

    static {
        CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS = new AnAction[CsvEditorSettings.EscapeCharacter.values().length + 1];
        for (int i = 0; i < CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS.length - 1; ++i) {
            CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS[i] = new CsvChangeEscapeCharacterAction(CsvEditorSettings.EscapeCharacter.values()[i]);
        }
        CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS[CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS.length - 1] = new CsvDefaultEscapeCharacterAction();
    }

    @Override
    public void update(AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
        Language language = psiFile == null ? null : psiFile.getLanguage();
        anActionEvent.getPresentation().setEnabledAndVisible(psiFile != null && language != null && language.isKindOf(CsvLanguage.INSTANCE));

        if (psiFile != null) {
            CsvEditorSettings.EscapeCharacter escapeCharacter = CsvFileAttributes.getInstance(psiFile.getProject()).getEscapeCharacter(psiFile);
            anActionEvent.getPresentation().setText(String.format("CSV Escape Character: %s", escapeCharacter.getDisplay()));
        }
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        return CSV_ESCAPE_CHARACTER_CHANGE_ACTIONS;
    }
}
