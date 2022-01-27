package unluac.decompile.statement;

import java.util.ArrayList;

import unluac.decompile.Decompiler;
import unluac.decompile.Output;
import unluac.decompile.Walker;
import unluac.decompile.expression.Expression;

public class Return extends Statement {

  private Expression[] values;
  
  public Return() {
    values = new Expression[0];
  }
  
  public Return(Expression value) {
    values = new Expression[1];
    values[0] = value;
  }
  
  public Return(Expression[] values) {
    this.values = values;
  }
  
  public void walk(Walker w) {
    w.visitStatement(this);
    for(Expression expression : values) {
      expression.walk(w);
    }
  }
  
  @Override
  public void print(Decompiler d, Output out) {
    out.print("do ");
    printTail(d, out);
    out.print(" end");
  }
  
  @Override
  public void printTail(Decompiler d, Output out) {
    out.print("return");
    if(values.length > 0) {
      out.print(" ");
      ArrayList<Expression> rtns = new ArrayList<Expression>(values.length);
      for(Expression value : values) {
        rtns.add(value);
      }
      Expression.printSequence(d, out, rtns, false, true);
    }
  }

}
