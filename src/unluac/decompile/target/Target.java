package unluac.decompile.target;

import unluac.decompile.Declaration;
import unluac.decompile.Decompiler;
import unluac.decompile.Output;
import unluac.decompile.Walker;

abstract public class Target {

  abstract public void walk(Walker w);
  
  abstract public void print(Decompiler d, Output out, boolean declare);
  
  abstract public void printMethod(Decompiler d, Output out);
  
  public boolean isDeclaration(Declaration decl) {
    return false;
  }
  
  public boolean isLocal() {
    return false;
  }
  
  public int getIndex() {
    throw new IllegalStateException();
  }
  
  public boolean isFunctionName() {
    return true;
  }
  
  public boolean beginsWithParen() {
    return false;
  }
  
}
