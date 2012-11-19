package gissystem.controllers;

import java.util.List;

import gissystem.datastructures.PrQuadtree;
import gissystem.interfaces.IPoint;
import gissystem.interfaces.IQuadTreeController;

/**
 * At this point, simply a wrapper class for the PrQuadtree. Better to extend the controller later on than any other class.
 * @author John
 *
 */
public class QuadTreeController implements IQuadTreeController {
	private PrQuadtree quadTree;
	
	public QuadTreeController() {
		this( null );
	}
	
	public QuadTreeController( PrQuadtree quadTree ) {
		this.quadTree = quadTree;
	}
	
	@Override
	public boolean insertToQuadTree( IPoint point, Long offset ) {
		return this.quadTree.insert( point, offset ); 
	}
	
	@Override
	public List<Long> findInQuadTree( IPoint point ) {
		return this.quadTree.find( point );
	}
	
	@Override
	public List<Long> findInQuadTree( long xLow, long xHigh, long yLow, long yHigh ) {
		return this.quadTree.find( xLow, xHigh, yLow, yHigh );
	}
	
	@Override
	public void setQuadTree( PrQuadtree quadTree ) {
		this.quadTree = quadTree;
	}
	
	@Override
	public String getQuadTreeToString() {
		return this.quadTree.toString();
	}
}
