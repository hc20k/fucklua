local a, b = f()
local test = b == 0
if a and ((test and not a.x) or (not test and not a.y)) then
  print(a)
end
