#!/bin/bash
# display line-of-comments/loc rate of parameters

LOC=0
EMPTY=0
COMMENT=0

printf "%-40s%8s%8s%8s\n" "File name" "LOC" "Comment" "SLOC"
while [ "$1" != '' ]
do
    this_loc=$(wc -l < "$1")
    this_comment=$(awk '/\/\// {print;}
    /\/\*/, /\*\// {print;}' "$1" | wc -l)
    this_empty=$(tr -d '\t ' < "$1" | grep '^$' | wc -l)
    this_sloc=$(($this_loc-$this_comment-$this_empty))
    printf "%-40s%8d%8d%8d\n" "$1" $this_loc $this_comment $this_sloc
    let LOC+=$this_loc
    let EMPTY+=$this_empty
    let COMMENT+=$this_comment
    shift
done
PERC=$(($COMMENT*100/($LOC+1)))
printf "%-40s%8d%8d%8d (%d%% comment)\n" "SUM" $LOC $COMMENT $(($LOC-$COMMENT-$EMPTY)) $PERC
