[![Build Status](https://travis-ci.org/sherbold/CrossPare.svg?branch=master)](https://travis-ci.org/sherbold/CrossPare)

Introduction
============

CrossPare is a tool designed for the execution of cross-project defect prediction experiments. It implements many techniques proposed by different research groups across the world.

CrossPare uses a mark-up style definition of experiments. In the end, it is a quite simple definition of an XML file. The XML file defines the following. 
- where the data for the experiment is stored and how it is loaded
- how the data shall be processed prior the the training of a prediction model
- the training of prediction models
  - it is possible to train and evaluate multiple prediction models with the same XML configuration file. Then all models use the same data processing steps.
- where and how the results shall be stored
  - CrossPare currently supports MySQL databases and CSV files. 
  
How does it work?
=================

Internally, CrossPare is centered around [Weka](https://weka.wikispaces.com/), a popular and powerful machine learning library for Java. Weka provides a wide variety of machine learning algorithms for the training of classification models as well as data processing methods readily available. However, CrossPare does not (yet) allow the direct access of the Weka functions. Instead, CrossPare requires wrappers around the Weka functionality. This is due to the nature of cross-project defect prediction, where the handling of training and test data may differ. Consider the following two examples:
- When applying a sampling method, e.g., undersampling, one may only sample the training data, but not the test data. 
- When applying a data standardization method, one may either only standardize the training data, only the test data, or both. 

Due to these differences, we require wrappers around Weka processors that define how the training and test data is treated. 

For the classification models, we are a bit more flexible. While CrossPare still provides different wrappers (e.g., "normal" training with all data, different kinds of local models, training separate classifiers for different products in the training data), these wrappers allow the usage of ANY weka compatible classifier within the classpath of CrossPare.

Once the processing and training is done, the results are evaluated, using many different metrics, e.g.,
- recall
- precision
- error rate
- F-measure
- G-measure
- Area Under the Curve (AUC)
- Matthews Correlation Coefficient (MCC)
- false positive rate
- false negative rate
- true positive rate (same as recall)
- true negative rate
- the confusion matrix itself.

The results are then stored in CSV files and optionally in a MySQL data base. 

CrossPare does not support the further evaluation of results. This needs to be performed by external scripts. It only supports the generation of results using different approaches. 

Citing CrossPare
================

In case you use CrossPare in your research, we would be happy if you cite our work. We published a short description of general framework of CrossPare at the SOFTMINE 2015 (available on our [research group's homepage](https://www.swe.informatik.uni-goettingen.de/publications/crosspare-tool-benchmarking-cross-project-defect-predictions) and in [IEEE Xplore](http://dx.doi.org/10.1109/ASEW.2015.8). Please use the paper as reference. 

Requirements
============

- Java 8

The master branch contains a fully configured Eclipse workspace as well as an Ant script for building. All used libraries are supplied directly with CrossPare. 

License
=======

CrossPare is licensed under the Apache License, Version 2.0. 
