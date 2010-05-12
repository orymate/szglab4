# hasznalat: awk -f classes.awk omlesztett.java
# omlesztett forrasfajlbol osztalyonkentibe valogatas
# bouml kimenetehez
/(class|interface)/ {
    for(i=1; i<NF; i++) {
        if ($i == "class" || $i == "interface")
            file = $(i+1);
    }
}
/(class|interface)/,/^}/ {
    print $0 > file ".java"
}
