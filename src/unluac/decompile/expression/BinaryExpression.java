package unluac.decompile.expression;

import unluac.decompile.Decompiler;
import unluac.decompile.Output;
import unluac.decompile.Walker;

public class BinaryExpression extends Expression {

  private final String op;
  private final Expression left;
  private final Expression right;
  private final int associativity;
  
  public static BinaryExpression replaceRight(BinaryExpression template, Expression replacement) {
    return new BinaryExpression(template.op, template.left, replacement, template.precedence, template.associativity);
  }
  
  public BinaryExpression(String op, Expression left, Expression right, int precedence, int associativity) {
    super(precedence);
    this.op = op;
    this.left = left;
    this.right = right;
    this.associativity = associativity;
  }

  @Override
  public void walk(Walker w) {
    w.visitExpression(this);
    left.walk(w);
    right.walk(w);
  }
  
  @Override
  public boolean isUngrouped() {
    return !beginsWithParen();
  }
  
  @Override
  public int getConstantIndex() {
    return Math.max(left.getConstantIndex(), right.getConstantIndex());
  }
  
  @Override
  public boolean beginsWithParen() {
    return leftGroup() || left.beginsWithParen();
  }
  
  @Override
  public void print(Decompiler d, Output out) {
    final boolean leftGroup = leftGroup();
    final boolean rightGroup = rightGroup();
    if(leftGroup) out.print("(");
    left.print(d, out);
    if(leftGroup) out.print(")");
    out.print(" ");
    out.print(op);
    out.print(" ");
    if(rightGroup) out.print("(");
    right.print(d, out);
    if(rightGroup) out.print(")");
  }
  
  public String getOp() {
    return op;
  }
  
  private boolean leftGroup() {
    return precedence > left.precedence || (precedence == left.precedence && associativity == ASSOCIATIVITY_RIGHT);
  }
  
  private boolean rightGroup() {
    return precedence > right.precedence || (precedence == right.precedence && associativity == ASSOCIATIVITY_LEFT);
  }
  
}
