LATEX = latex -interaction=batchmode

FEJEZETEK = 02_Kovetelmenyprojektfunkcionalitas.tex 03_Analizismodellkidolgozasa.tex 04_Analizismodellkidolgozasa.tex 05_Szkeletontervezese.tex 06_Szkeletonbeadas.tex 07_Prototipuskoncepcioja.tex 08_Reszletestervek.tex 10_Prototipusbeadasa.tex 11_Grafikusfeluletspecifikacioja.tex 13_Grafikusvaltozatbeadasa.tex 14_Osszefoglalas.tex
IMAGES = #img/fig128005.eps img/fig128067.eps ...
DEPS = magyar.ldf fedlap.sty Makefile head.tex bib.tex $(IMAGES) 
# :read! find ../prototests -type f | grep -v svn | tr '\n' ' '

all.pdf: all.ps

clean:
	rm -f *.log *.aux *.pdf *.dvi *.ps *.toc *.lof highlight.sty proto.ebnf.tex


%.pdf: %.ps
	@echo '====> ps2pdf'
	@ps2pdf $< $@ > $*.pdf.log

%.ps: %.dvi
	@echo '====> dvips'
	@dvips $< 2> $*.ps.log

all.dvi: all.tex ${FEJEZETEK} ${DEPS}

UNDEFINED = '((Reference|Citation).*undefined)|(Label.*multiply defined)'
%.dvi: %.tex
	@echo "====> LaTeX first pass $<"
	@touch .rerun
	@i=0;                                                                           \
	 while [ -f .rerun ] &&  [ $$i -le 5 ] ; do                                     \
	   rm -f .rerun;                                                                \
	   i=`expr $$i + 1`;                                                            \
	   printf "====> LaTeX round %d\n" $$i;                                         \
	   $(LATEX) $< || true;                                                         \
	   if grep '[Rr]erun' $*.log || grep 'No file .*\.toc' $*.log ; then            \
	     touch .rerun; echo Rerun or notoc;                                         \
	   fi;                                                                          \
	   ( grep '[Ee]rror' $*.log && touch .rerun; ) || true;                         \
	 done                                                                           
	@echo "====> Undefined references and citations:"
	@egrep -i $(UNDEFINED) $*.log || echo "None."

#highlight.sty: proto.ebnf.tex
##
#proto.ebnf.tex: ../misc/proto.ebnf 
#	 highlight -E ../misc -S ebnf ../misc/proto.ebnf -L | sed  -e '/end {doc/,$$ d' -e '1,/begin{doc/ d' > $@
#	 sed -i -e 's/?/\\mbox{?}/g' $@
%.eps: %.png
	convert $< $@
