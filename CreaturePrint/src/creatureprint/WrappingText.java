package creatureprint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class WrappingText {

    private String text; // Text to be separated
    private PDFont font; // Font used
    private float width; // how long the lines can be
    private float fontSize; // how big the font is
    private float lineWidth; // how long the longest line is
    
    public WrappingText(String text, PDFont font, float width, float fontSize) {
        this.text = text;
        this.font = font;
        this.width = width;
        this.fontSize = fontSize;
        lineWidth = 0;
    }

    public WrappingText(String text, float width, float fontSize)  {
    	 this.text = text;
         this.font = PDType1Font.TIMES_ROMAN;
         this.width = width;
         this.fontSize = fontSize;
         lineWidth = 0;
    }
    public float getTotalLineWidth() {
    	return width;
    }
    public float getLineWidth() throws IOException{
    	if (lineWidth == 0)
    		getStraightLines();
    	return lineWidth;
    }
    public String getText() {
    	return text;
    }
    /**
     * Separates the text into lines with some formatting
     *  - #I = italic text
     *  - #H = bold text
     *  - #U = superscript text
     * @return the lines
     * @throws IOException
     */
    public List<String> getParagraphLines() throws IOException {
        List<String> result = new ArrayList<String>();
        String[] split = text.split("(?<=[-—,\\s])");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }
        boolean title = false, table = false;
        int start = 0, end = 0, tableCounter = 0;
        float width = font.getStringWidth("    ") / 1000 * fontSize, wordWidth;
        for ( int i : possibleWrapPoints ) {
        	if (title) {
        		if (text.substring(end, i).trim().endsWith("#")) {
        			result.add(text.substring(start,i));
        			start = i;
        			title = false;
        		}
        		end = i;
        	}
        	else if (table) {
        		if (text.charAt(i - 3) == '#' && text.charAt(i - 2) == 'E')
        			if (--tableCounter == 0) {
        				table = false;
        				result.add(text.substring(start, i));
        				start = i;
        				width = 0;
        				end = i;
        			}
        	}
        	else {
        		if (text.substring(end, i).contains("#")) {
        			//get parts of word before effects
        			wordWidth = font.getStringWidth(text.substring(end, text.indexOf("#", end))) / 1000 * fontSize;
        			if (text.charAt(text.indexOf("#", end) + 1) == 'I') {
        				if (text.substring(text.indexOf("#I", end) + 2, i).contains("#")) {
        					wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(
        							text.indexOf("#I", end) + 2, text.indexOf("#", text.indexOf("#I", end) + 2))) / 1000 * fontSize;
        					wordWidth += font.getStringWidth(text.substring(
        							text.indexOf("#", text.indexOf("#I", end) + 2) + 1, i)) / 1000 * fontSize;
        				}
        				else 
        					wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(text.indexOf("#I", end) + 2, i)) / 1000 * fontSize;
        			}
        			else if (text.charAt(text.indexOf("#", end) + 1) == 'H') {
        				if (text.substring(text.indexOf("#H", end) + 2, i).contains("#")) {
        					wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(
        							text.indexOf("#H", end) + 2, text.indexOf("#", text.indexOf("#H", end) + 2))) / 1000 * fontSize;
        					wordWidth += font.getStringWidth(text.substring(
        							text.indexOf("#", text.indexOf("#H", end) + 2) + 1, i)) / 1000 * fontSize;
        				}
        				else
        					wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(text.indexOf("#H", end) + 2, i)) / 1000 * fontSize;
        			}
        			else if (text.charAt(text.indexOf("#", end) + 1) == 'U') {
        				if (text.substring(text.indexOf("#U", end) + 2, i).contains("#")) {
        					wordWidth += font.getStringWidth(text.substring(
        							text.indexOf("#U", end) + 2, text.indexOf("#", text.indexOf("#U", end) + 2))) / 1000 * fontSize * 4.5f / 9.5;
        					wordWidth += font.getStringWidth(text.substring(
        							text.indexOf("#", text.indexOf("#U", end) + 2) + 1, i)) / 1000 * fontSize;
        				}
        				else
        					wordWidth += font.getStringWidth(text.substring(text.indexOf("#U", end) + 2, i)) / 1000 * fontSize * 4.5f / 9.5;
        			}
        			else if (text.charAt(text.indexOf("#", end) + 1) == 'P') {
        				if (!text.substring(start, end).equals(""))
        					result.add(text.substring(start,end));
        				start = end;
        				if (lineWidth < width)
        					lineWidth = width;
        				width = font.getStringWidth("    ") / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(text.indexOf("#P", end) + 2, i)) / 1000 * fontSize;
        			}
        			else if (text.charAt(text.indexOf("#", end) + 1) == 'T') {
        				result.add(text.substring(start,end));
        				start = end;
        				if (lineWidth < width)
        					lineWidth = width;
        				width = 0;
        				if (text.indexOf("#", text.indexOf("#", end) + 1) < i) {
        					result.add(text.substring(start,i));
                			start = i;
        				}
        				else
        					title = true;
        			}
        			else if (text.charAt(text.indexOf("#", end) + 1) == 'C') {
        				result.add(text.substring(start,end));
        				start = end;
        				tableCounter = Integer.parseInt(text.substring((text.indexOf("#", end) + 2), (text.indexOf("x", end))));
        				int headers = tableCounter;
        				tableCounter *= Integer.parseInt(text.substring((text.indexOf("x", end)) + 1, (text.indexOf("#", end + 2))));
        				tableCounter += headers;
        				table = true;
        			}
        		}
        		//if there aren't any special effects in the word
        		else
        			wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        		if ( start < end && width + wordWidth > this.width ) {
        			result.add(text.substring(start,end));
        			start = end;
        			if (lineWidth < width)
    					lineWidth = width;
        			width = 0;
        		}
        		width += wordWidth;
        		end = i;
        	}
        }
        if (lineWidth < width)
			lineWidth = width;
        result.add(text.substring(start));
        return result;
    }

    /**
     * Separates the text into lines with some formatting
     * 	- has hanging indent
     *  - #I = italic text
     *  - #H = bold text
     *  - #U = superscript text
     * @return the lines
     */
    public List<String> getFancyLines() throws IOException {
    	List<String> result = new ArrayList<String>();
    	String[] split = text.split("(?<=[-—,\\s])");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }
        int start = 0, end = 0;
        float width = 0, wordWidth;
        for ( int i : possibleWrapPoints ) {
        	if (text.substring(end, i).contains("#")) {
        		//get parts of word before effects
        		wordWidth = font.getStringWidth(text.substring(end, text.indexOf("#", end))) / 1000 * fontSize;
        		if (text.charAt(text.indexOf("#", end) + 1) == 'I') {
        			if (text.substring(text.indexOf("#I", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(
        						text.indexOf("#I", end) + 2, text.indexOf("#", text.indexOf("#I", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#I", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(text.indexOf("#I", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'H') {
        			if (text.substring(text.indexOf("#H", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(
        						text.indexOf("#H", end) + 2, text.indexOf("#", text.indexOf("#H", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#H", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(text.indexOf("#H", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'U') {
        			if (text.substring(text.indexOf("#U", end) + 2, i).contains("#")) {
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#U", end) + 2, text.indexOf("#", text.indexOf("#U", end) + 2))) / 1000 * fontSize * 4.5f / 9.5;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#U", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += font.getStringWidth(text.substring(text.indexOf("#U", end) + 2, i)) / 1000 * fontSize * 4.5f / 9.5;
        		}
        		else
        			wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        	}
        	//if there aren't any special effects in the word
        	else
        		wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        	if ( start < end && width + wordWidth > this.width ) {
                result.add(text.substring(start,end));
                start = end;
                if (lineWidth < width)
					lineWidth = width;
                width = font.getStringWidth("    ") / 1000 * fontSize;
            }
        	width += wordWidth;
            end = i;
        }
        if (lineWidth < width)
			lineWidth = width;
        result.add(text.substring(start));
        return result;
    }
    /**
     * Separates the text into lines with some formatting
     * 	- has no indent
     *  - #I = italic text
     *  - #H = bold text
     *  - #U = superscript text
     * @return the lines
     */
    public List<String> getStraightLines() throws IOException {
    	List<String> result = new ArrayList<String>();
    	String[] split = text.split("(?<=[-—,\\s])");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }
        int start = 0, end = 0;
        float width = 0, wordWidth;
        for ( int i : possibleWrapPoints ) {
        	if (text.substring(end, i).contains("#")) {
        		//get parts of word before effects
        		wordWidth = font.getStringWidth(text.substring(end, text.indexOf("#", end))) / 1000 * fontSize;
        		if (text.charAt(text.indexOf("#", end) + 1) == 'I') {
        			if (text.substring(text.indexOf("#I", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(
        						text.indexOf("#I", end) + 2, text.indexOf("#", text.indexOf("#I", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#I", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(text.indexOf("#I", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'H') {
        			if (text.substring(text.indexOf("#H", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(
        						text.indexOf("#H", end) + 2, text.indexOf("#", text.indexOf("#H", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#H", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(text.indexOf("#H", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'U') {
        			if (text.substring(text.indexOf("#U", end) + 2, i).contains("#")) {
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#U", end) + 2, text.indexOf("#", text.indexOf("#U", end) + 2))) / 1000 * fontSize * 4.5f / 9.5;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#U", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += font.getStringWidth(text.substring(text.indexOf("#U", end) + 2, i)) / 1000 * fontSize * 4.5f / 9.5;
        		}
        		else
        			wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        	}
        	//if there aren't any special effects in the word
        	else
        		wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        	if ( start < end && width + wordWidth > this.width ) {
                result.add(text.substring(start,end));
                start = end;
                if (lineWidth < width)
					lineWidth = width;
                width = 0;
            }
        	width += wordWidth;
            end = i;
        }
        if (lineWidth < width)
			lineWidth = width;
        result.add(text.substring(start));
        return result;
    }
    /**
     * Separates the text into lines with some formatting
     *  - #I = italic text
     *  - #H = bold text
     *  - #U = superscript text
     * @return the lines
     */
    public List<String> getIndentedLines() throws IOException {
        List<String> result = new ArrayList<String>();
        String[] split = text.split("(?<=[-—,\\s])");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }
        int start = 0, end = 0;
        float width = font.getStringWidth("    ") / 1000 * fontSize, wordWidth;
        for ( int i : possibleWrapPoints ) {
        	if (text.substring(end, i).contains("#")) {
        		//get parts of word before effects
        		wordWidth = font.getStringWidth(text.substring(end, text.indexOf("#", end))) / 1000 * fontSize;
        		if (text.charAt(text.indexOf("#", end) + 1) == 'I') {
        			if (text.substring(text.indexOf("#I", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(
        						text.indexOf("#I", end) + 2, text.indexOf("#", text.indexOf("#I", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#I", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else 
        				wordWidth += PDType1Font.TIMES_ITALIC.getStringWidth(text.substring(text.indexOf("#I", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'H') {
        			if (text.substring(text.indexOf("#H", end) + 2, i).contains("#")) {
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(
        						text.indexOf("#H", end) + 2, text.indexOf("#", text.indexOf("#H", end) + 2))) / 1000 * fontSize;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#H", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += PDType1Font.TIMES_BOLD.getStringWidth(text.substring(text.indexOf("#H", end) + 2, i)) / 1000 * fontSize;
        		}
        		else if (text.charAt(text.indexOf("#", end) + 1) == 'U') {
        			if (text.substring(text.indexOf("#U", end) + 2, i).contains("#")) {
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#U", end) + 2, text.indexOf("#", text.indexOf("#U", end) + 2))) / 1000 * fontSize * 4.5f / 9.5;
        				wordWidth += font.getStringWidth(text.substring(
        						text.indexOf("#", text.indexOf("#U", end) + 2) + 1, i)) / 1000 * fontSize;
        			}
        			else
        				wordWidth += font.getStringWidth(text.substring(text.indexOf("#U", end) + 2, i)) / 1000 * fontSize * 4.5f / 9.5;
        		}
        	}
        	//if there aren't any special effects in the word
        	else
        		wordWidth = font.getStringWidth(text.substring(end, i)) / 1000 * fontSize;
        	if ( start < end && width + wordWidth > this.width ) {
                result.add(text.substring(start,end));
                start = end;
                if (lineWidth < width)
					lineWidth = width;
                width = font.getStringWidth("        ") / 1000 * fontSize;
            }
        	width += wordWidth;
            end = i;
        }
        if (lineWidth < width)
			lineWidth = width;
        result.add(text.substring(start));
        return result;
    }
}