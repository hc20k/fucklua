-- or�������� ������ if ��ʾ�� ��Ҫ�м����
function debug_or()
  local f,a,b
  fg = ((not a) or b)
  -- if (a) then x = b else x = true end; fg = x
end

-- or������, �����ж϶����� loadbool true������loadbool�ĺ�䣩
function debug_or2()
  local f,a,b,x,y
  fg = ((not a) or x<y or b)
end

-- and�������������� if ��ʾ�� ��Ҫ�м����
function debug_and_call()
  local f,a,b,c
  fg = ((a<b) and c)
  -- if (a<b) then x = c else x = false end; fg = x
end

-- and�������������� if ��ʾ���ѷ������ if����ԭ�ȱ���һ�� loadbool
function debug_and_set()
  local f,a,b,c
  f = ((a<b) and c)
  -- if (a<b) then f = c else f = false end;
end

-- ��� or and ������ loadbool �� false true ����ʹ��
function debug_or_and()
  local f,a,b,x,y
  fg=(not a) or x<y and d==e and x
end
