<?php header("Content-Type: text/html; charset=UTF-8"); 

if (isset($_POST['who'])) {
  $f = fopen('naplo.tex', 'a');
  if ($f) {
    $dur = $_POST['duration'];
    if ($dur % 60 >= 30)
      $dur = ((int)($dur/60)) . ',5 óra';
    else
      $dur = ((int)($dur/60)) . ' óra';
    $what = str_replace("\r\n", " %\n  ", wordwrap($_POST['desc'], 68, "\r\n"));

    fwrite(
      $f, 
      sprintf(
        "%16s & %10s & %10s & %%\n  %s \\\\\n", 
        $_POST['when'], 
        $dur, 
        $_POST['who'], 
        $what
      )
    );
  }
  else {
    $err = 'Sikertelen írás.';
  }
}

?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<title>Scremento napló</title>

</head>

<body>
<?php if ($err): ?>
<h1>Hiba</h1>
<p><?php print $err ?></p>
<?php endif; ?>
<form action="naplo.php" method="post">
<h1>Új bejegyzés</h1>
<p><label>Kezdés időpontja: <input type="text" value="<?php print date("Y-m-d H:i") ?>" name="when" /></label></p>
<p><label>Időtartam: <input type="text" value="" name="duration" />&nbsp;perc</label></p>
<p><label>Részvetők: <select name="who"><option value="">--</option>
    <option>Gipsz</option>
    <option>Jakab</option>
    <option>Gipsz, Jakab</option>
</select></label></p>
<p><label>Összefoglalás: <textarea name="desc" rows="4" cols="72"></textarea></label></p>
<p><input type="submit"/></p>
</form>

<h1>Eddigi bejegyzések</h1>
<pre><?php print htmlspecialchars(file_get_contents('naplo.tex'));?></pre>
</body>
</html>
