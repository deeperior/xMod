CREATE TABLE IF NOT EXISTS blockwatcher
(id int(15) auto_increment not null,
x int(5),
y int(5),
z int(5)),
world string(25),
primary key (id));

CREATE TABLE IF NOT EXISTS player
(id int(3) auto_increment not null,
pickaxe int(11),
axe int(11),
shovel int(11),
shear int(11),
how int(11),
repair(11)
primary key(id));

 ? 