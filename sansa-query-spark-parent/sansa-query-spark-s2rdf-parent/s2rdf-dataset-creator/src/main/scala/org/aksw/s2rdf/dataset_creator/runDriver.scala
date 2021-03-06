/* Copyright Simon Skilevic
 * Master Thesis for Chair of Databases and Information Systems
 * Uni Freiburg
 */

package org.aksw.s2rdf.dataset_creator

object runDriver {
  def main(args:Array[String]){
    // parse Args
    Settings.loadUserSettings(args(0), args(1), args(3).toFloat)
    val datasetType = args(2)
    DataSetGenerator.generateDataSet(datasetType);
  }
}