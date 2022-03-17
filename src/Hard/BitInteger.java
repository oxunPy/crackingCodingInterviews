package Hard;

public class BitInteger {
    public static int INTEGER_SIZE;
    private String binaryString;

    public BitInteger(String binaryString){
        this.binaryString = binaryString;
        setIntegerSize();
    }

    private void setIntegerSize(){
        if(!binaryString.isEmpty()){
            for(int i = 0; i < binaryString.length(); i++){
                if(binaryString.charAt(i) == '1'){
                    INTEGER_SIZE = i + 1;
                    break;
                }
            }
        }
    }

    public int fetch(int col){
        return Character.getNumericValue(binaryString.charAt(col)) - Character.getNumericValue('0');
    }
}
