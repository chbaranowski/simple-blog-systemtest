import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;


public class Main
{
  
  public static void main(String[] args) throws CompilationFailedException, IOException
  {
    Binding binding = new Binding();
    Person value = new Person();
    binding.setVariable("person", value);
    binding.setVariable("age", "30");
    
    GroovyShell shell = new GroovyShell(binding );
    Object evaluate = shell.evaluate(new File("simple.groovy"));
    System.out.println(value.permision);
  }
  
}
