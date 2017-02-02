package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Question41939016 {

    public static void main(String args[])throws IOException
    {
        char a,b,c;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter three digits");
        a=(char)br.read();
        b=(char)br.read();
        c=(char)br.read();
        String s;
        s=""+a+b+c;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int k=0;k<3;k++)
                {
                    if(i!=j && i!=k)
                        System.out.println(""+s.charAt(i)+s.charAt(j)+s.charAt(k));
                }
            }
        }
    }
}
