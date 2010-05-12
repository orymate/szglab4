LATEX = latex -interaction=batchmode

FEJEZETEK = 02_Kovetelmenyprojektfunkcionalitas.tex 03_Analizismodellkidolgozasa.tex 04_Analizismodellkidolgozasa.tex 05_Szkeletontervezese.tex 06_Szkeletonbeadas.tex 07_Prototipuskoncepcioja.tex 08_Reszletestervek.tex 10_Prototipusbeadasa.tex 11_Grafikusfeluletspecifikacioja.tex 13_Grafikusvaltozatbeadasa.tex 14_Osszefoglalas.tex
IMAGES = #img/fig128005.eps img/fig128067.eps ...
DEPS = magyar.ldf fedlap.sty Makefile head.tex bib.tex $(IMAGES) #proto.ebnf.tex highlight.sty $(PROTOTEST) jdoc.tex 10_sub_testcases.tex 11_sub_jdoc.tex
#PROTOTEST = ../prototests/1/map.txt ...
# :read! find ../prototests -type f | grep -v svn | tr '\n' ' '

all.pdf: all.ps

clean:
	rm -f *.log *.aux *.pdf *.dvi *.ps *.toc *.lof highlight.sty proto.ebnf.tex


%.pdf: %.ps
	ps2pdf $< $@

%.ps: %.dvi
	dvips $<

all.dvi: all.tex ${FEJEZETEK} ${DEPS}

RERUN = '(undefined references|[rR]erun|No file .*\.toc)'
RERUNBIB = 'No file.*\.bbl|Citation.*undefined'
UNDEFINED = '((Reference|Citation).*undefined)|(Label.*multiply defined)'
%.dvi: %.tex
	@test ! -f all.toc || cp all.toc all.old.toc
	@echo "====> LaTeX first pass $<"
	@$(LATEX) $< || $(LATEX) $< 
	@if ! cmp all.toc all.old.toc -s ; then echo "====> LaTeX rerun" && $(LATEX) $<; fi
	@if egrep -q $(RERUN) $*.log ; then echo "====> LaTeX rerun" && $(LATEX) $<; fi
	@if egrep -q $(RERUN) $*.log ; then echo "====> LaTeX rerun" && $(LATEX) $<; fi
	@echo "====> Undefined references and citations:"
	@egrep -i $(UNDEFINED) $*.log || echo "None."
	@echo "====> Dimensions:"
	@grep "dimension:" $*.log || echo "None."

#highlight.sty: proto.ebnf.tex
#
#proto.ebnf.tex: ../misc/proto.ebnf 
#	 highlight -E ../misc -S ebnf ../misc/proto.ebnf -L | sed  -e '/end {doc/,$$ d' -e '1,/begin{doc/ d' > $@
#	 sed -i -e 's/?/\\mbox{?}/g' $@
%.eps: %.png
	convert $< $@
