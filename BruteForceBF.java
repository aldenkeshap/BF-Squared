import java.util.regex.Pattern;
public class BruteForceBF {
	public static class Patterns {
		final static Pattern first = Pattern.compile("\\[[^+-]*\\]");
		final static Pattern second = Pattern.compile("\\[[+-]\\]");
		final static Pattern third = Pattern.compile("\\[[+-]*\\]");
		//Pattern fourth = Pattern.compile("\\[[^+-]*\\]");
	}
    public static String interpret(String code) {
    	final int LENGTH = 255;
    	final int MAXNUM = 255;
    	final int MAXLOOP = countOccurrences(code, '[') == 1 ? 256 : 65536;
        int[] mem = new int[LENGTH];
        int dataPointer= 0;
        int l = 0;
        int loops = 0;
        String output = "";
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                dataPointer = (dataPointer == LENGTH-1) ? 0 : dataPointer + 1;
            } else if(code.charAt(i) == '<') {
                dataPointer = (dataPointer == 0) ? LENGTH-1 : dataPointer - 1;
            } else if(code.charAt(i) == '+') {
            	if (mem[dataPointer] != MAXNUM) {
            		mem[dataPointer]++;
            	} else {
            		mem[dataPointer] = 0;
            	}
            } else if(code.charAt(i) == '-') {
            	if (mem[dataPointer] != 0) {
            		mem[dataPointer]--;
            	} else {
            		mem[dataPointer] = MAXNUM;
            	}
            } else if(code.charAt(i) == '.') {
                output += (char) mem[dataPointer];
            } else if(code.charAt(i) == '[') {
                if(mem[dataPointer] == 0) {
                    i++;
                    while((l > 0 || code.charAt(i) != ']') && i < MAXNUM) {
                        if(code.charAt(i) == '[') l++;
                        if(code.charAt(i) == ']') l--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(mem[dataPointer] != 0 && loops < MAXLOOP) {
                	loops++;
                    i--;
                    while(l > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') l++;
                        if(code.charAt(i) == '[') l--;
                        i--;
                    }
                    i--;
                }
            }
        }
    //System.out.println(loops);
    return output;
    }
    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i=0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                 count++;
            }
        }
        return count;
    }
	public static Boolean checkBrackets(String code){
		int openParens = 0;
		for (int i=0; i<code.length(); i++) {
			char chr = code.charAt(i);
			if (chr == '[') {
				openParens++;
			} else if (chr == ']') {
				if (openParens == 0) {
					return false;
				} else {
					openParens--;
				}
			}
		}
		return openParens == 0;
	}
    public static boolean pass(String code) {
    	return code.contains("+-") || code.contains("-+") || code.contains("[]") || 
    			code.contains("][") ||code.contains("><") || code.contains("<>") || 
    			Patterns.first.matcher(code).find() || 
    			(!Patterns.second.matcher(code).find() && 
    					Patterns.third.matcher(code).find());
    }
	public static String BruteForce(String text) {
		System.out.println("Starting to Brute Force " + text);
		int i = -1;
		while (true) {
			i++;
			String code = Integer.toOctalString(i);
			if (code.contains("7")) {continue;}
			code = code.replace("0", "+"); //><+-[].
			code = code.replace("1", "-"); //+-[]><.
			code = code.replace("2", "[");
			code = code.replace("3", "]");
			code = code.replace("4", ">");
			code = code.replace("5", "<");
			code = code.replace("6", ".");
			if (!code.contains(".")) {continue;}
			else if (countOccurrences(code, '[') != countOccurrences(code, ']')) {continue;}
			else if (!((code.startsWith("+")||code.startsWith("-"))&&(code.endsWith("+")||code.endsWith("-")||code.endsWith(".")||code.endsWith("]")))) {continue;}
			else if (countOccurrences(code, '.') > text.length()) {continue;}
			else if (!checkBrackets(code)) {continue;}
			else if (pass(code)) {continue;}
			//System.out.println(code + Integer.toString(i));
			String value;
			try {value = interpret(code);}
			catch (Exception e) {value = "";}
			if (value.equals(text)) {
				return code;
			}
		}
	}
	public static void main(String[] args) {
		//System.out.println(interpret("+[>>>--]."));
		long start = System.currentTimeMillis();
		System.out.println(BruteForce("ú"));
		long end = System.currentTimeMillis();
		System.out.println("Done, started at " + Long.toString(start) + " ended at " + Long.toString(end) + " which is " + Long.toString(end-start));
		java.awt.Toolkit.getDefaultToolkit().beep();
	}

}
