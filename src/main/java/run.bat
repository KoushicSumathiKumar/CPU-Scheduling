@echo off
javac *.java 2>warning.log

for /l %%x in (1, 1, 3) do (
   echo - EXPERIMENT %%x TAKING PLACE...
   for /l %%y in (1, 1, 5) do (
      for /l %%z in (1, 1, 5) do (
         javaw InputGenerator ../../../experiment%%x/inputs/input_parameters%%z.prp ../../../experiment%%x/inputs/inputs%%z.in
         javaw Simulator ../../../experiment%%x/schedulers/simulator_parameters%%y.prp ../../../experiment%%x/outputs/scheduler%%y/output%%z.out ../../../experiment%%x/inputs/inputs%%z.in
      )
      echo SCHEDULERS %%y OUPUTS ARE READY
   )
   echo EXPERIMENT %%x COMPLETE
   echo  -----------------------------
)
pause