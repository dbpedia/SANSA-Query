package net.sansa_stack.query.spark.server

import org.aksw.sparqlify.backend.postgres.DatatypeToStringCast
import org.aksw.sparqlify.core.sql.common.serialization.SqlEscaperBase
import org.aksw.sparqlify.core.algorithms.ViewDefinitionNormalizerImpl
import org.aksw.sparqlify.core.algorithms.CandidateViewSelectorSparqlify
import net.sansa_stack.rdf.partition.sparqlify.SparqlifyUtils2
import org.apache.spark.sql.catalyst.ScalaReflection
import net.sansa_stack.rdf.spark.sparqlify.BasicTableInfoProviderSpark
import org.aksw.sparqlify.util.SqlBackendConfig
import org.aksw.sparqlify.algebra.sql.nodes.SqlOpTable
import org.apache.spark.sql.types.StructType
import org.aksw.sparqlify.config.syntax.Config
import org.aksw.sparqlify.util.SparqlifyUtils
import org.aksw.sparqlify.validation.LoggerCount
import net.sansa_stack.rdf.partition.core.RdfPartitionDefault
import org.apache.spark.sql.SparkSession
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.aksw.sparqlify.core.interfaces.SparqlSqlStringRewriter

object SparqlifyUtils3
  extends LazyLogging
{
  def createSparqlSqlRewriter(sparkSession: SparkSession, partitions: Map[RdfPartitionDefault, RDD[Row]]): SparqlSqlStringRewriter = {
    val config = new Config()
    val loggerCount = new LoggerCount(logger.underlying)


    val backendConfig = new SqlBackendConfig(new DatatypeToStringCast(), new SqlEscaperBase("", "")) //new SqlEscaperBacktick())
    val sqlEscaper = backendConfig.getSqlEscaper()
    val typeSerializer = backendConfig.getTypeSerializer()


    val ers = SparqlifyUtils.createDefaultExprRewriteSystem()
    val mappingOps = SparqlifyUtils.createDefaultMappingOps(ers)


    val candidateViewSelector = new CandidateViewSelectorSparqlify(mappingOps, new ViewDefinitionNormalizerImpl());

    val views = partitions.map {
      case (p, rdd) =>

        println("Processing: " + p)

        val vd = SparqlifyUtils2.createViewDefinition(p);
        val tableName = vd.getRelation match {
          case o: SqlOpTable => o.getTableName
          case _ => throw new RuntimeException("Table name required - instead got: " + vd)
        }

        val scalaSchema = p.layout.schema
        val sparkSchema = ScalaReflection.schemaFor(scalaSchema).dataType.asInstanceOf[StructType]
        val df = sparkSession.createDataFrame(rdd, sparkSchema)

        df.createOrReplaceTempView(tableName)
        config.getViewDefinitions.add(vd)
    }

    val basicTableInfoProvider = new BasicTableInfoProviderSpark(sparkSession)

    val rewriter = SparqlifyUtils.createDefaultSparqlSqlStringRewriter(basicTableInfoProvider, null, config, typeSerializer, sqlEscaper)
    //val rewrite = rewriter.rewrite(QueryFactory.create("Select * { <http://dbpedia.org/resource/Guy_de_Maupassant> ?p ?o }"))

//    val rewrite = rewriter.rewrite(QueryFactory.create("Select * { ?s <http://xmlns.com/foaf/0.1/givenName> ?o ; <http://dbpedia.org/ontology/deathPlace> ?d }"))
    rewriter
  }

}