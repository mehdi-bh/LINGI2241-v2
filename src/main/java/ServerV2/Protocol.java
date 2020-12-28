package ServerV2;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Protocol {
    private static final int N_TYPES = 6;

    public String processInput(String clientInput) {
        // Check si les arguments sont bons
        if(!clientInput.contains(";")){
            return ("Asked format : <types>;<regex>\n");
        }

        // Récupère les arguments
        String[] input = clientInput.split(";");

        Set<String> setTypes = new TreeSet<String>();
        if(!input[0].equals("")){
            setTypes.addAll(Arrays.asList(input[0].split(",")));
        }

        // Linear search
        Pattern p = Pattern.compile(input[1]);
        Matcher m;
        int nbContains = 0;
        long start = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < N_TYPES; i++) {
            if(setTypes.size() == 0 || setTypes.contains(String.valueOf(i))){
                for(String sentence : ServerLaunch.DATABASE[i]){
                    m = p.matcher(sentence);
                    nbContains++;
                    if(m.find()) builder.append(sentence);
                }
            }
        }
        System.out.println("nb test : " + nbContains + " | computing time : " + (System.currentTimeMillis() - start));
        builder.append("\n");
        return builder.toString();
    }
}