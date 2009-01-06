About
=====

GeneticLeonardo is a program that "evolves" drawings. The chromosomes encode the position, shape and color of 50 polygons. The fittest chromosomes are those who produce images most like a given image.

The goal is to approximate any image with only 50 polygons.

Here's how it works:

1.  Generate a random population of chromosomes

2.  Produce images by interpreting these chromosomes

3.  Select the chromosomes that produce the best images

4.  Mate the best chromosomes by exchanging information

5.  Mutate the new chromosomes slightly

6.  Kill the worst ranked chromosomes

7.  Repeat from step 2 until satisfied

Download
=========

An executable version can be downloaded from:

[http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-0.1.zip](http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-0.1.zip)

Contributing
============

You are more than welcome to contribute to this project! There is room for a lot of improvement.

Source code
-----------

The code is released as Open Source under the BSD license.

A zip of the source code is available at:

[http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-src-0.1.zip](http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-src-0.1.zip)

A zip of an earlier version which uses Genetic Programming is available too:

[http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-gp-src-0.1.zip](http://github.com/ynd/genetic-drawing/raw/master/genetic_drawing-gp-src-0.1.zip)

Note that the GP version doesn't converge very well yet.

Source guide
------------

The most important classes are:

* [gd.gui.EvolutionRunnable](http://github.com/ynd/genetic-drawing/tree/master/src/gd/gui/EvolutionRunnable.java): Class in charge of actually running the evolution process.

* [gd.core.GAConfiguration](http://github.com/ynd/genetic-drawing/tree/master/src/gd/core/GAConfiguration.java): Encapsulates the settings of the genetic algorithm.

* [gd.core.GAInitialChromosomeFactory](http://github.com/ynd/genetic-drawing/tree/master/src/gd/core/GAInitialChromosomeFactory.java): Creates a suitable initial chromosome.

* [gd.core.GAPhenotypeExpresser](http://github.com/ynd/genetic-drawing/tree/master/src/gd/core/GAPhenotypeExpresser.java): Class that can interpret the information encoded in the chromosomes.

* [gd.core.LMSFitnessFunction](http://github.com/ynd/genetic-drawing/tree/master/src/gd/core/LMSFitnessFunction.java): Computes the fitness of a program as the Least-Mean-Sqare distance between the image it generates and the target image.

Screenshot
==========
[![Cool hu?](http://github.com/ynd/genetic-drawing/raw/master/media/screen-small.png)](http://github.com/ynd/genetic-drawing/raw/master/media/screen.png)