-- dummy
function test0()
  return
end

-- test1 test2 �Ƿ�������ĵط�
function test1()
  local f
  f(not (nil and f(nil)))
end

function test2()
  local f
  f = (not (nil and f(nil)))
end

-- test3 test4 �ֽ�����ͬ���ɼ����߼����ʽ�Ż��� test4 �Ż��� test3
function test3()
  local f,a,b
  f = (not (a and b))
end

function test4()
  local f,a,b
  f = ((not a) or (not b))
end

-- test5 ������ if ��ʾ�� �ڲ������м����������£��� test6 �߼����ʽ��ͬ
function test5()
  local f,a,b
  f((not a) or b)
end

-- test6 ���������ʽ �� ��һ���޷�ִ�е���LOADBOOL�� or������ ִ�к�һ�� LOADBOOL true 
function test6()
  local f,a,b
  f = (not a) or b
end

-- test7 �� if ��ʾ test6
function test7()
  local f,a,b
  if (a) then
    f = b
  else
    f = true
  end
end

-- test8 ��һ�������ʽ�� ��һ���޷�ִ�е���LOADBOOL�� or������ ִ�к�һ�� LOADBOOL true 
function test8()
  local f,a,b,c
  f = a > b or c
end

-- test9 �� if ��ʾ test8 
function test9()
  local f,a,b,c
  if ( a<=b ) then -- �� (not a>b) ��һ��
    f = c
  else
    f = true
  end
end

-- testand0 �޷��� if���߼����ʽ�� testand3 ��ͬ
function testand0()
  local f,a,b,c
  f((a<b) and c)
end

-- testand1 ��Ӧ�� and ��ʾ�� ͬ���Ƕ�һ���޷�ִ�е���LOADBOOL��and������ ִ��ǰһ�� LOADBOOL false skip
function testand1()
  local f,a,b,c
  f = (not a) and c
end

-- testand2 �� if ��ʾ testand1
function testand2()
  local f,a,b,c
  if (not a) then
    f = c
  else
    f = false
  end
end

-- testand3 ��Ӧ�� and ��ʾ�� ͬ���Ƕ�һ���޷�ִ�е���LOADBOOL��and������ ִ��ǰһ�� LOADBOOL false skip
function testand3()
  local f,a,b,c
  f = (a<b) and c
end

-- testand4 �� if ��ʾ testand3
function testand4()
  local f,a,b,c
  if (a<b) then
    f = c
  else
    f = false
  end
end
