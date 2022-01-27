package unluac.decompile.target;

import unluac.decompile.Declaration;
import unluac.decompile.Decompiler;
import unluac.decompile.Output;
import unluac.decompile.Walker;

public class VariableTarget extends Target {

  public final Declaration decl;
  
  public VariableTarget(Declaration decl) {
    this.decl = decl;
  }
  
  @Override
  public void walk(Walker w) {}
  
  @Override
  public void print(Decompiler d, Output out, boolean declare) {
    out.print(decl.name);
    if(declare && decl.tbc) {
      out.print(" <close>");
    }
  }
  
  @Override
  public void printMethod(Decompiler d, Output out) {
    throw new IllegalStateException();
  }
  
  @Override
  public boolean isDeclaration(Declaration decl) {
    return this.decl == decl;
  }  
  
  @Override
  public boolean isLocal() {
    return true;
  }
  
  @Override
  public int getIndex() {
    return decl.register;
  }
  
  public boolean equals(Object obj) {
    if(obj instanceof VariableTarget) {
      VariableTarget t = (VariableTarget) obj;
      return decl == t.decl;
    } else {
      return false;
    }
  }
  
}
