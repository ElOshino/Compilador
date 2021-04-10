package lenguajepalabras.example;


public class infijaAPrefija {
    int ap1=0;
    char []p=new char[20];
    
   public  infijaAPrefija(){
    for(int i=0; i<20; i++){
    p[i]='#';
    }
    }
        
    
    public String convertir(String exp){
    String  cadena=""; 
    String salida="";
    String rta="";
    
    for(int i=(exp.length()-1); i>=0; i--) {
    cadena=cadena+exp.charAt(i);
    }   
    System.out.println(cadena);    
        
    for(int i=0; i<cadena.length(); i++){
        if(cadena.charAt(i)=='+'||cadena.charAt(i)=='-'||cadena.charAt(i)=='*'||cadena.charAt(i)=='^'
          ||cadena.charAt(i)=='/'||cadena.charAt(i)=='('||cadena.charAt(i)==')'){
            
            if(cadena.charAt(i)=='('||cadena.charAt(i)==')'){
                for(int x=ap1 ; x>=0; x--){
                
                    if(p[x]!=')'&&p[x]!='#'){
                    salida=salida+p[x];  
                    p[x]='#';
                    }
                
                }
               
                
            }            
            else{
        p[ap1]=cadena.charAt(i);
         }
        ap1++;
    }   
    else    {
    salida=salida+cadena.charAt(i);
    
    }    
        
    } 
    
    for(int x=0; x<ap1; x++){
    if(p[x]!='#'){
    salida=salida+p[x];  
    }
    }
    System.out.println(salida);
    
    for(int i=(salida.length()-1); i>=0; i--) {
    if(salida.charAt(i)!='#'){
        rta=rta+salida.charAt(i);
    }
    }
      
    return rta;
    }
            
    
    
    
    
}
