import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BruteForceBF {
    public static String interpret(String code) {
    	final int LENGTH = 65535;
    	final int MAXNUM = 255;
        int[] mem = new int[LENGTH];
        int dataPointer= 0;
        int l = 0;
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
                if(mem[dataPointer] != 0) {
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
    public static boolean pass(String code) {
    	Pattern r = Pattern.compile("\\[[^+-]*\\]");
    	return r.matcher(code).find();
    }
	public static String BruteForce(String text) {
		int i = -1;
		while (true) {
			i++;
			String code = Integer.toOctalString(i);
			if (code.contains("7")) {continue;}
			code = code.replace("0", ">");
			code = code.replace("1", "<");
			code = code.replace("2", "+");
			code = code.replace("3", "-");
			code = code.replace("4", "[");
			code = code.replace("5", "]");
			code = code.replace("6", ".");
			if (code.split("\\[", -1).length-1 != code.split("\\]", -1).length-1) {continue;}
			else if (pass(code)) {continue;}
			System.out.println(code + Integer.toString(i));
			String value;
			try {value = interpret(code);}
			catch (Exception e) {value = "";}
			if (value.equals(text)) {
				return code;
			}
		}
	}
	public static void main(String[] args) {
    		System.out.println("Test with BruteForce(\"String To Brute Generate\")");
	}

}