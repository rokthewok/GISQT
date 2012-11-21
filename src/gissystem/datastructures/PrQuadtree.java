package gissystem.datastructures;

import gissystem.interfaces.IPoint;
import gissystem.helpers.Direction;

import java.util.Vector;

public class PrQuadtree { 
 
	abstract class prQuadNode { } 
	class prQuadLeaf extends prQuadNode { 
		Vector<prIndex> indices;
		
		public prQuadLeaf( IPoint point, Long offset ) {
			this.indices = new Vector<prIndex>( bucketSize );
			this.indices.add( new prIndex( point, offset ) );
		}
		
		public boolean containsCoordinates( IPoint point ) {
			for( prIndex index : this.indices ) {
				if( index.point.equals( point ) ) {
					return true;
				}
			}
			
			return false;
		}
		
		public void insert( IPoint point, Long offset ) {
			for( prIndex index : this.indices ) {
				if( index.point.equals( point ) ) {
					index.offsets.add( offset );
					break;
				}
			}
		}
		
		public void add( IPoint point, Long offset ) {
			this.indices.add( new prIndex( point, offset ) );
		}
	} 
	class prQuadInternal extends prQuadNode {
		prQuadNode NE;
		prQuadNode NW;
		prQuadNode SE;
		prQuadNode SW;
		
		public prQuadInternal() {
			this.NE = null;
			this.NW = null;
			this.SE = null;
			this.SW = null;
		}
	}
	class prIndex {
		IPoint point;
		Vector<Long> offsets;
		public prIndex( IPoint point, Long offset ) {
			this.point = point;
			this.offsets = new Vector<Long>();
			if( offset != null ) {
				this.offsets.add( offset );
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( "Index at (" );
			sb.append( this.point.getX() );
			sb.append( ", " );
			sb.append( this.point.getY() );
			sb.append( ") offsets: " );
			sb.append( this.offsets.toString() );
			
			return sb.toString();
		}
	}
	  
	prQuadNode root; 
	long xMin, xMax, yMin, yMax;
	int bucketSize;
	private String leafClassName = "gissystem.datastructures.PrQuadtree$prQuadLeaf";
	
	/**
	 * ctor. Default bucket size is 10.
	 * @param xMin The lower x-bound of the world map.
	 * @param xMax The upper x-bound of the world map.
	 * @param yMin The lower y-bound of the world map.
	 * @param yMax The upper y-bound of the world map.
	 */
	public PrQuadtree(long xMin, long xMax, long yMin, long yMax) { 
		this( xMin, xMax, yMin, yMax, 10 );
	}
	
	/**
	 * ctor.
	 * @param xMin The lower x-bound of the world map.
	 * @param xMax The upper x-bound of the world map.
	 * @param yMin The lower y-bound of the world map.
	 * @param yMax The upper y-bound of the world map.
	 * @param bucketSize The depth of each leaf node's bucket
	 */
	public PrQuadtree( long xMin, long xMax, long yMin, long yMax, int bucketSize ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.bucketSize = bucketSize;
				
		this.root = null;
	}
	  
	/**
	 * Attempts to insert an offset with corresponding point in the tree.  
	 * @param point The coordinates at which the offset is located.
	 * @param offset The offset value from the database.
	 * @return true if there is a successful insertion, else false
	 */
	public boolean insert( IPoint point, Long offset ) { 
		if( point == null || offset == null ) {
			return false;
		}
		
		// outside of world bounds
		if( point.inQuadrant( this.xMin, this.xMax, this.yMin, this.yMax ) == Direction.NOQUADRANT ) {
			return false;
		}
		
		// case the tree is empty
		if( this.root == null ) {
			this.root = new prQuadLeaf( point, offset );
			return true;
		}
		
		// the root is a leaf
		if( this.root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) this.root;
			if( leaf.containsCoordinates( point ) ) {
				// the leaf has that coordinate
				leaf.insert( point, offset );
			} else if( leaf.indices.size() < this.bucketSize ) {
				// the leaf does not have the coordinate, and is not at max capacity
				leaf.add( point, offset );
			} else if( leaf.indices.size() == this.bucketSize ) {
				// the leaf does not have the coordinate and is at max capacity
				this.root = new prQuadInternal();
				// do a reinsertion of all the existing items in the leaf
				for( prIndex index : leaf.indices ) {
					for( Long off : index.offsets ) {
						if( !insert( null, this.root, null, index.point, off, this.xMin, this.xMax, this.yMin, this.yMax ) ) {
							return false;
						}
					}
				}
				
				return insert( null, this.root, null, point, offset, this.xMin, this.xMax, this.yMin, this.yMax );
			}
			
			return true;
		}
		
		// the root is an internal node
		return insert( null, this.root, null, point, offset, this.xMin, this.xMax, this.yMin, this.yMax );
	}
	
	private boolean insert( prQuadInternal parent, prQuadNode child, Direction directionFrom, IPoint point,
							Long offset, long xMin, long xMax, long yMin, long yMax ) {
		// case this is an empty leaf node
		if( child == null ) {
			child = new prQuadLeaf( point, offset );
			if( parent != null ) {
				if( directionFrom == Direction.SW ) {
					parent.SW = child;
				} else if( directionFrom == Direction.SE ) {
					parent.SE = child;
				} else if( directionFrom == Direction.NW ) {
					parent.NW = child;
				} else {
					parent.NE = child;
				}
			}
			return true;
		}
		
		// case there is already a data point in the leaf
		if( child.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) child;
			if( leaf.containsCoordinates( point ) ) {
				// the leaf has that coordinate
				leaf.insert( point, offset );
			} else if( leaf.indices.size() < this.bucketSize ) {
				// the leaf does not have the coordinate, and is not at max capacity
				leaf.add( point, offset );
			} else {
				// the leaf does not have the coordinate and is at max capacity
				child = new prQuadInternal();
				if( parent != null ) {
					if( directionFrom == Direction.SW ) {
						parent.SW = child;
					} else if( directionFrom == Direction.SE ) {
						parent.SE = child;
					} else if( directionFrom == Direction.NW ) {
						parent.NW = child;
					} else {
						parent.NE = child;
					}
				}
				// reinsert all the items in the leaf
				for( prIndex index : leaf.indices ) {
					for( Long off : index.offsets ) {
						if( !insert( parent, child, directionFrom, index.point, off, xMin, xMax, yMin, yMax ) ) {
							return false;
						}
					}
				}
				
				return insert( parent, child, directionFrom, point, offset, xMin, xMax, yMin, yMax );
			}
			
			return true;
		} else {
			// this is an internal node
			prQuadInternal node = (prQuadInternal) child;
			Direction result = point.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				return this.insert( node, node.SW, Direction.SW, point, offset, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				return this.insert( node, node.SE, Direction.SE, point, offset, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				return this.insert( node, node.NW, Direction.NW, point, offset, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				// if it returns NOQUADRANT, it is the origin, and should be placed in the NE quadrant
				return this.insert( node, node.NE, Direction.NE, point, offset, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}
  
	/**
	 * Delete all the offsets associated with a given point. 
	 * @param point The point to which a list of offsets corresponds.
	 * @return true if the deletion is successful; else false.
	 */
	public boolean delete( IPoint point ) {
		if( point == null ) {
			return false;
		}
		
		// empty tree
		if( this.root == null ) {
			return false;
		}
		
		// root is a leaf; check if it equals the element to delete
		if( this.root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) this.root;
			if( leaf.indices.get( 0 ).equals( point ) ) {
				this.root = null;
				return true;
			} else {
				return false;
			}
		} else {
			// the root is an internal node
			prQuadInternal node = (prQuadInternal) this.root;
			boolean success;
			Direction result = point.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				success = this.delete( node, node.SW, Direction.SW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				success = this.delete( node, node.SE, Direction.SE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				success = this.delete( node, node.NW, Direction.NW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				success = this.delete( node, node.NE, Direction.NE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
			
			// if all the leaves of the internal node are null, retract the node
			if( allLeavesAreNull( node ) ) {
				this.root = null;
			}
			return success;
		}
	}
	
	private boolean delete( prQuadInternal parent, prQuadNode child, Direction directionFrom,
							IPoint point, long xMin, long xMax, long yMin, long yMax ) {
		if( point == null ) {
			return false;
		}
		
		if( child == null ) {
			return false;
		}
		
		// recursion has reached an endpoint; check if it's the correct element
		if( child.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) child;
			for( prIndex index : leaf.indices ) {
				if( index.point.equals( point ) ) {
					leaf.indices.remove( index );
					
					// if that was the only index in the leaf, set that leaf to null
					if( parent != null && leaf.indices.size() == 0 ) {
						if( directionFrom == Direction.SW ) {
							parent.SW = null;
						} else if( directionFrom == Direction.SE ) {
							parent.SE = null;
						} else if( directionFrom == Direction.NW ) {
							parent.NW = null;
						} else {
							parent.NE = null;
						}
					}
					
					return true;
				}
			}
			
			return false;
		} else {
			// this is an internal node; keep going!
			prQuadInternal node = (prQuadInternal) child;
			boolean success;
			Direction result = point.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				success = this.delete( node, node.SW, Direction.SW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				success = this.delete( node, node.SE, Direction.SE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				success = this.delete( node, node.NW, Direction.NW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				success = this.delete( node, node.NE, Direction.NE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
			// if all or all but one the leaves of the internal node are null, retract the node
			tryRetractNode( parent, node, directionFrom );
			return success;
		}
	}
	
	private void tryRetractNode( prQuadInternal parent, prQuadInternal node, Direction directionFrom ) {
		if( allLeavesAreNull( node ) ) {
			// all the leaves are null; set the parent leaves null and retract
			setParentLeavesNull( parent, directionFrom );
			// rest of cases, only one leaf is not null
		} else if( node.SW != null && threeLeavesAreNull( node.SE, node.NW, node.NE ) && node.SW.getClass().getName().equals( this.leafClassName ) ) {
			Vector<prIndex> indices = ( (prQuadLeaf) node.SW ).indices;
			setParentLeavesNull( parent, directionFrom );
			for( prIndex index : indices ) {
				for( Long offset : index.offsets ) {
					insert( index.point, offset );
				}
			}
		} else if( node.SE != null && threeLeavesAreNull( node.SW, node.NW, node.NE ) && node.SE.getClass().getName().equals( this.leafClassName ) ) {
			Vector<prIndex> indices = ( (prQuadLeaf) node.SE ).indices;
			setParentLeavesNull( parent, directionFrom );
			for( prIndex index : indices ) {
				for( Long offset : index.offsets ) {
					insert( index.point, offset );
				}
			}
		} else if( node.NE != null && threeLeavesAreNull( node.SW, node.SE, node.NW ) && node.NE.getClass().getName().equals( this.leafClassName ) ) {
			Vector<prIndex> indices = ( (prQuadLeaf) node.NE ).indices;
			setParentLeavesNull( parent, directionFrom );
			for( prIndex index : indices ) {
				for( Long offset : index.offsets ) {
					insert( index.point, offset );
				}
			}
		} else if( node.NW != null && threeLeavesAreNull( node.SW, node.NE, node.SE ) && node.NW.getClass().getName().equals( this.leafClassName ) ) {
			Vector<prIndex> indices = ( (prQuadLeaf) node.NW ).indices;
			setParentLeavesNull( parent, directionFrom );
			for( prIndex index : indices ) {
				for( Long offset : index.offsets ) {
					insert( index.point, offset );
				}
			}
		} else;
	}

	private boolean allLeavesAreNull( prQuadInternal node ) {
		return node.NE == null && node.NW == null && node.SE == null && node.SW == null;
	}
	
	private boolean threeLeavesAreNull( prQuadNode nodeOne, prQuadNode nodeTwo, prQuadNode nodeThree ) {
		return nodeOne == null && nodeTwo == null && nodeThree == null;
	}
	
	private void setParentLeavesNull( prQuadInternal parent, Direction directionFrom ) {
		if( directionFrom == Direction.SW ) {
			parent.SW = null;
		} else if( directionFrom == Direction.SE ) {
			parent.SE = null;
		} else if( directionFrom == Direction.NW ) {
			parent.NW = null;
		} else {
			parent.NE = null;
		}
	}
	
	/**
	 * Finds all the offsets at a given point.
	 * @param point The point to which the offsets correspond.
	 * @return A Vector of offets or null if the point is not in the tree.
	 */
	public Vector<Long> find( IPoint point ) { 
		return this.find( this.root, point, this.xMin, this.xMax, this.yMin, this.yMax );
	}
	
	private Vector<Long> find( prQuadNode root, IPoint point, long xMin, long xMax, long yMin, long yMax ) {
		// case not in bounds
		if( point.inQuadrant( xMin, xMax, yMin, yMax ) == Direction.NOQUADRANT ) {
			return null;
		}
		// case this is an empty leaf node
		if( root == null ) {
			return null;
		}
		
		// case there is data in the leaf
		if( root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) root;
			for( prIndex index : leaf.indices ) {
				if( index.point.equals( point ) ) {
					return index.offsets;
				}
			}
			return null;
		}
		
		// case this is an internal node
		else {
			prQuadInternal node = (prQuadInternal) root;
			Direction result = point.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				return this.find( node.SW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				return this.find( node.SE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				return this.find( node.NW, point, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				return this.find( node.NE, point, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}
	
	/**
	 * Finds all the offsets within a given set of boundaries.
	 * @param xLo The lower x-bound of the region.
	 * @param xHi The upper x-bound of the region.
	 * @param yLo The lower y-bound of the region.
	 * @param yHi The upper y-bound of the region.
	 * @return A Vector of offsets or null if no offsets are located in the region.
	 */
	public Vector<Long> find(long xLo, long xHi, long yLo, long yHi) {
		Vector<Long> offsets = new Vector<Long>();
		find( offsets, this.root, xLo, xHi, yLo, yHi, this.xMin, this.xMax, this.yMin, this.yMax );
		
		return offsets;
   }
   
	private void find( Vector<Long> offsets, prQuadNode root, long xLo, long xHi, long yLo, long yHi,
							long xMin, long xMax, long yMin, long yMax ) {
	   // case empty node
		if( root == null ) {
			return;
		}
		// case rectangle is outside boundary coordinates
		if( xLo > xMax || xHi < xMin || yLo > yMax || yHi < yMin ) {
			return;
		} else {
			if( root.getClass().getName().equals( this.leafClassName ) ) {
				prQuadLeaf leaf = (prQuadLeaf) root;
				for( prIndex index : leaf.indices ) {
					if( index.point.inQuadrant( xLo, xHi, yLo, yHi ) != Direction.NOQUADRANT ) {
						offsets.addAll( index.offsets );
					}
				}
			} else {
				prQuadInternal internal = (prQuadInternal) root;
				find( offsets, internal.SW, xLo, xHi, yLo, yHi, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
				find( offsets, internal.SE, xLo, xHi, yLo, yHi, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
				find( offsets, internal.NW, xLo, xHi, yLo, yHi, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
				find( offsets, internal.NE, xLo, xHi, yLo, yHi, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}

	/**
	 * Creates a String depiction of the tree. Nodes are written out in NE, NW, SE, SW order. UPDATE: now prints in SW, SE, NE, NW order
	 * @return the String representation of the tree.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		appendSubtree( this.root, sb, "" );
		return sb.toString();
	}
	
	/*
	 * helper function for toString()
	 */
	private void appendSubtree( prQuadNode root, StringBuilder sb, String padding ) {
		// empty leaf = *
		// internal node = []
		String extraPadding = "   ";
		if( root == null ) {
			sb.append( padding + "*" );
		} else if( root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) root;
			sb.append( padding );
			for( prIndex index : leaf.indices ) {
				sb.append( index.toString() );
				sb.append( " | " );
			}
		} else {
			prQuadInternal node = (prQuadInternal) root;
			appendSubtree( node.SW, sb, padding + extraPadding );
			sb.append( "\n" );
			appendSubtree( node.SE, sb, padding + extraPadding );
			sb.append( "\n" );
			sb.append( padding + "[]" );
			sb.append( "\n" );
			appendSubtree( node.NE, sb, padding + extraPadding );
			sb.append( "\n" );
			appendSubtree( node.NW, sb, padding + extraPadding );
		}
	}
}
