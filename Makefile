JAVAC = /usr/bin/javac
.SUFFIXES: .java .class
SRCDIR = src
BINDIR = bin
DOCDIR = doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= Vector.class CloudData.class CloudClassSeq.class ParallelAvg.class ParallelClassify.class RunCloudClass.class 
CLASS_FILES= $(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

run: 
	java -cp bin/ RunCloudClass

clean:
	rm $(BINDIR)/*.class

doc:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

doc_clean:
	rm -rf $(DOCDIR)
