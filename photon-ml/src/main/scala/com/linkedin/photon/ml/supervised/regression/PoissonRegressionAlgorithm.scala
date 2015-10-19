package com.linkedin.photon.ml.supervised.regression

import breeze.linalg.Vector
import com.linkedin.photon.ml.function.TwiceDiffFunction
import com.linkedin.photon.ml.data.LabeledPoint
import com.linkedin.photon.ml.function.{PoissonLossFunction, TwiceDiffFunction}
import com.linkedin.photon.ml.optimization.RegularizationContext
import com.linkedin.photon.ml.supervised.model.GeneralizedLinearAlgorithm
import com.linkedin.photon.ml.util.DataValidators


/**
 * Train a regression model using L2-regularized poisson regression.
 * @author asaha
 */
class PoissonRegressionAlgorithm extends GeneralizedLinearAlgorithm[PoissonRegressionModel, TwiceDiffFunction[LabeledPoint]] {

  override protected val validators = List(DataValidators.finiteFeaturesValidator, DataValidators.finiteLabelValidator, DataValidators.nonNegativeLabelValidator)

  /* Objective function = loss function + l2weight * regularization */
  override protected def createObjectiveFunction(regularizationContext: RegularizationContext, regularizationWeight: Double): TwiceDiffFunction[LabeledPoint] = {
    TwiceDiffFunction.withRegularization(new PoissonLossFunction, regularizationContext, regularizationWeight)
  }

  /**
   * Create a poisson regression model given the estimated coefficients and intercept
   */
  override protected def createModel(coefficients: Vector[Double], intercept: Option[Double]) = {
    new PoissonRegressionModel(coefficients, intercept)
  }
}