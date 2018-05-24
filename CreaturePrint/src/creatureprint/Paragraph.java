package creatureprint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Paragraph {

    /** width of this paragraph */
    private int width;

    /** text to write */
    private String text;

    /** font to use */
    private PDFont font;

    /** font size to use */
    private int fontSize;

    public Paragraph(int width, PDFont font, int fontSize, String text) {
        this.text = text;
        this.font = font;
        this.width = width;
        this.fontSize = fontSize;
    }

    /**
     * Break the text in lines
     * @return
     */
    public List<String> getLines() throws IOException {
        List<String> result = new ArrayList<String>();

        String[] split = text.split("(?<=\\W)");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }

        int start = 0;
        int end = 0;
        for ( int i : possibleWrapPoints ) {
            float width = font.getStringWidth(text.substring(start,i)) / 1000 * fontSize;
            if ( start < end && width > this.width ) {
                result.add(text.substring(start,end));
                start = end;
            }
            end = i;
        }
        // Last piece of text
        result.add(text.substring(start));
        return result;
    }

    public float getFontHeight() {
        return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
    }

    public int getWidth() {
        return width;
    }

    public String getText() {
        return text;
    }

    public PDFont getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }
    public static void main(String[] args) {
    	PDFont font = PDType1Font.TIMES_ROMAN;
    	int fontSize = 10;
    	String longText = "Nine lantern archons can fuse together as a full-round action, becoming a single Large entity that is more powerful than the individual archons that make up its parts. Looking like a whirlwind of dancing firefly lights, the gestalt has all the powers and abilities of a Large air elemental plus the following: archon, good, and lawful subtypes; archon traits (aura of menace DC 16); 2 light rays (2d6); DR 5/evil and magic. The archons can remain in this form for 2d4 rounds. When the gestalt separates back into individual lantern archons, its remaining hit points are divided evenly among them; if it had less than 9 hit points, some of the component archons die when the gestalt ends." + "";
    	try (PDDocument pdoc = new PDDocument();) {
			PDPage page = new PDPage();
			PDPageContentStream contentStream = new PDPageContentStream(pdoc, page);
			contentStream.setFont( font, fontSize );
			System.out.println(font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize);
			contentStream.setLeading(font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize);
			Paragraph paragraph = new Paragraph(200, font, fontSize, longText);
			contentStream.beginText();
			contentStream.newLineAtOffset(.9f * 72, PDRectangle.LETTER.getHeight() - (.9f * 72));
	    	for (String line : paragraph.getLines()) {
	    	    // increase value of position Y to mimic line breaking
	    	    contentStream.showText(line);
	    	    contentStream.newLine();
	    	}
	    	contentStream.endText();
			contentStream.close();
			pdoc.addPage(page);
			pdoc.save("/home/brandon/Documents/StatBloccs/Hollow'sLastHope/NewFile.pdf");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
}