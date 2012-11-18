package gissystem.interfaces;

import gissystem.datastructures.PrQuadtree;

import java.util.List;

public interface IQuadTreeController {
	public boolean insertToQuadTree( IPoint point, Long offset );
	public List<Long> findInQuadTree( IPoint point );
	public List<Long> findInQuadTree( long xLow, long xHigh, long yLow, long yHigh );
	public void setQuadTree( PrQuadtree quadTree );
}
