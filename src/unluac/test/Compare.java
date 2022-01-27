package unluac.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import unluac.Configuration;
import unluac.parse.BHeader;
import unluac.parse.LFunction;
import unluac.parse.LLocal;
import unluac.parse.LObject;

public class Compare {

  public static enum Mode {
    NORMAL,
    FULL,
  }
  
  private final Mode mode;
  
  public Compare(Mode mode) {
    this.mode = mode;
  }
  
  /**
   * Determines if two files of lua bytecode are the same
   * (except possibly for line numbers).
   */
  public boolean bytecode_equal(String file1, String file2) {
    LFunction main1 = file_to_function(file1);
    LFunction main2 = file_to_function(file2);
    return function_equal(main1, main2);
  }

  public boolean function_equal(LFunction f1, LFunction f2) {
    if(f1.maximumStackSize != f2.maximumStackSize) {
      return false;
    }
    if(f1.numParams != f2.numParams) {
      return false;
    }
    if(f1.numUpvalues != f2.numUpvalues) {
      return false;
    }
    if(f1.vararg != f2.vararg) {
      return false;
    }
    if(f1.code.length != f2.code.length) {
      return false;
    }
    for(int i = 0; i < f1.code.length; i++) {
      if(f1.code[i] != f2.code[i]) {
        return false;
      }
    }
    if(f1.constants.length != f2.constants.length) {
      return false;
    }
    for(int i = 0; i < f1.constants.length; i++) {
      if(!object_equal(f1.constants[i], f2.constants[i])) {
        return false;
      }
    }
    if(f1.locals.length != f2.locals.length) {
      return false;
    }
    for(int i = 0; i < f1.locals.length; i++) {
      if(!local_equal(f1.locals[i], f2.locals[i])) {
        return false;
      }
    }
    if(f1.upvalues.length != f2.upvalues.length) {
      return false;
    }
    for(int i = 0; i < f1.upvalues.length; i++) {
      if(!f1.upvalues[i].equals(f2.upvalues[i])) {
        return false;
      }
    }
    if(f1.functions.length != f2.functions.length) {
      return false;
    }
    for(int i = 0; i < f1.functions.length; i++) {
      if(!function_equal(f1.functions[i], f2.functions[i])) {
        return false;
      }
    }
    if(mode == Mode.FULL) {
      if(!f1.name.equals(f2.name)) {
        return false;
      }
      if(f1.linedefined != f2.linedefined) {
        return false;
      }
      if(f1.lastlinedefined != f2.lastlinedefined) {
        return false;
      }
      if(f1.lines.length != f2.lines.length) {
        return false;
      }
      for(int i = 0; i < f1.lines.length; i++) {
        if(f1.lines[i] != f2.lines[i]) {
          return false;
        }
      }
      if((f1.abslineinfo == null) != (f2.abslineinfo == null)) {
        return false;
      }
      if(f1.abslineinfo != null) {
        if(f1.abslineinfo.length != f2.abslineinfo.length) {
          return false;
        }
        for(int i = 0; i < f1.abslineinfo.length; i++) {
          if(!f1.abslineinfo[i].equals(f2.abslineinfo[i])) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public boolean object_equal(LObject o1, LObject o2) {
    return o1.equals(o2);
  }
  
  public boolean local_equal(LLocal l1, LLocal l2) {
    if(l1.start != l2.start) {
      return false;
    }
    if(l1.end != l2.end) {
      return false;
    }
    if(!l1.name.equals(l2.name)) {
      return false;
    }
    return true;
  }
  
  public LFunction file_to_function(String filename) {
    RandomAccessFile file = null;
    try {
      file = new RandomAccessFile(filename, "r");
      ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
      buffer.order(ByteOrder.LITTLE_ENDIAN);
      int len = (int) file.length();
      FileChannel in = file.getChannel();
      while(len > 0) len -= in.read(buffer);
      buffer.rewind();
      BHeader header = new BHeader(buffer, new Configuration());
      return header.main;
    } catch(IOException e) {
      return null;
    } finally {
      if(file != null) {
        try {
          file.close();
        } catch(IOException e) {}
      }
    }
  }
  
}
