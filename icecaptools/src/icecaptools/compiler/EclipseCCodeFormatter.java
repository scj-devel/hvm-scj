package icecaptools.compiler;

import java.util.HashMap;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class EclipseCCodeFormatter implements IcecapCodeFormatter {

    private CodeFormatter codeFormatter;
    
    public EclipseCCodeFormatter()
    {
        HashMap<String, String> options = CCorePlugin.getOptions();
        codeFormatter = org.eclipse.cdt.core.ToolFactory.createDefaultCodeFormatter(options);
    }
    
    public String format(String source) {
        TextEdit edit = codeFormatter.format(0, source, 0, source.length(), 0, null);
        
        IDocument document= new Document(source);
        String formattedSource;
        
        try {
            edit.apply(document);
            formattedSource = document.get();
        } catch (MalformedTreeException e) {
            formattedSource = source;
        } catch (BadLocationException e) {
            formattedSource = source;
        }
        return formattedSource;
    }
}
