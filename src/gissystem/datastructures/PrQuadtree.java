package gissystem.datastructures;

import gissystem.interfaces.IPoint;
import gissystem.helpers.Direction;

import java.util.Vector;

public class PrQuadtree< T extends IPoint> { 
 
	abstract class prQuadNode { } 
	class prQuadLeaf extends prQuadNode { 
		Vector<prIndex> Elements;
		
		public prQuadLeaf() {
			this( null );
		}
		
		public prQuadLeaf( T element ) {
			this.Elements = new Vector<prIndex>( bucketSize );
			this.Elements.add( new prIndex( element.getX(), element.getY(), element ) );
		}
		
		public boolean containsCoordinates( T element ) {
			for( prIndex index : this.Elements ) {
				if( index.x == element.getX() && index.y == element.getY() ) {
					return true;
				}
			}
			
			return false;
		}
		
		public void insert( T element ) {
			for( prIndex index : this.Elements ) {
				if( index.x == element.getX() && index.y == element.getY() ) {
					index.offsets.add( element );
					break;
				}
			}
		}
		
		public void add( T element ) {
			this.Elements.add( new prIndex( element.getX(), element.getY(), element ) );
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
		long x;
		long y;
		Vector<T> offsets;
		public prIndex( long x, long y, T element ) {
			this.x = x;
			this.y = y;
			this.offsets = new Vector<T>();
			if( element != null ) {
				this.offsets.add( element );
			}
		}
	}
	  
	prQuadNode root; 
	long xMin, xMax, yMin, yMax;
	int bucketSize;
	private String leafClassName = "Minor.P3.DS.prQuadtree$prQuadLeaf";
	
	// Initialize quadtree to empty state, representing the specified region. 
	public PrQuadtree(long xMin, long xMax, long yMin, long yMax) { 
		this( xMin, xMax, yMin, yMax, 10 );
	}
	
	public PrQuadtree( long xMin, long xMax, long yMin, long yMax, int bucketSize ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.bucketSize = bucketSize;
				
		this.root = null;
	}
	  
	// Pre:   elem != null 
	// Post:  If elem lies within the tree's region, and elem is not already  
	//        present in the tree, elem has been inserted into the tree. 
	// Return true iff elem is inserted into the tree.  
	@SuppressWarnings("unchecked")
	public boolean insert( T elem ) { 
		if( elem == null ) {
			return false;
		}
		
		// outside of world bounds
		if( elem.inQuadrant( this.xMin, this.xMax, this.yMin, this.yMax ) == Direction.NOQUADRANT ) {
			return false;
		}
		
		// case the tree is empty
		if( this.root == null ) {
			this.root = new prQuadLeaf( elem );
			return true;
		}
		
		// the root is a leaf
		if( this.root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) this.root;
			if( leaf.Elements.size() < this.bucketSize ) {
				if( leaf.containsCoordinates( elem ) ) {
					leaf.insert( elem );
				} else {
					leaf.add( elem );
				}
			} else if( leaf.Elements.size() == this.bucketSize ) {
				this.root = new prQuadInternal();
				// TODO add reinsert index method
				for( prIndex index : leaf.Elements ) {
					for( T element : index.offsets ) {
						if( !insert( null, this.root, null, element, this.xMin, this.xMax, this.yMin, this.yMax ) ) {
							return false;
						}
					}
					
					return true;
				}
			}
		}
		
		// the root is an internal node
		return insert( null, this.root, null, elem, this.xMin, this.xMax, this.yMin, this.yMax );
	}
	
	@SuppressWarnings("unchecked")
	private boolean insert( prQuadInternal parent, prQuadNode child, Direction directionFrom,
							T elem, long xMin, long xMax, long yMin, long yMax ) {
		// case this is an empty leaf node
		if( child == null ) {
			child = new prQuadLeaf( elem );
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
			if( leaf.Elements.size() < this.bucketSize ) {
				if( leaf.containsCoordinates( elem ) ) {
					leaf.insert( elem );
				} else {
					leaf.add( elem );
				}
				
				return true;
			} else { // size is >= bucketSize
				this.root = new prQuadInternal();
				// TODO add reinsert index method
				for( prIndex index : leaf.Elements ) {
					for( T element : index.offsets ) {
						if( !insert( null, this.root, null, element, this.xMin, this.xMax, this.yMin, this.yMax ) ) {
							return false;
						}
					}
				}
				
				return true;
			}
		} else { // this is an internal node
			prQuadInternal node = (prQuadInternal) child;
			Direction result = elem.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				return this.insert( node, node.SW, Direction.SW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				return this.insert( node, node.SE, Direction.SE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				return this.insert( node, node.NW, Direction.NW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else { // if it returns NOQUADRANT, it is the origin, and should be placed in the NE quadrant
				return this.insert( node, node.NE, Direction.NE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}
	  
	// Pre:  elem != null 
	// Post: If elem lies in the tree's region, and a matching element occurs 
	//       in the tree, then that element has been removed. 
	// Returns true iff a matching element has been removed from the tree. 
	@SuppressWarnings("unchecked")
	public boolean delete(IPoint Elem) {
		if( Elem == null ) {
			return false;
		}
		
		// empty tree
		if( this.root == null ) {
			return false;
		}
		
		// root is a leaf; check if it equals the element to delete
		if( this.root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) this.root;
			if( leaf.Elements.get( 0 ).equals( Elem ) ) {
				this.root = null;
				return true;
			} else {
				return false;
			}
		} else {
			prQuadInternal node = (prQuadInternal) this.root;
			boolean success;
			Direction result = Elem.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				success = this.delete( node, node.SW, Direction.SW, Elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				success = this.delete( node, node.SE, Direction.SE, Elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				success = this.delete( node, node.NW, Direction.NW, Elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				success = this.delete( node, node.NE, Direction.NE, Elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
			
			// if all the leaves of the internal node are null, retract the node
			if( allLeavesAreNull( node ) ) {
				this.root = null;
			}
			return success;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean delete( prQuadInternal parent, prQuadNode child, Direction directionFrom,
							IPoint elem, long xMin, long xMax, long yMin, long yMax ) {
		if( elem == null ) {
			return false;
		}
		
		if( child == null ) {
			return false;
		}
		
		// recursion has reached an endpoint; check if it's the correct element
		if( child.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) child;
			if( leaf.Elements.get( 0 ).equals( elem ) ) {
				if( parent != null ) {
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
			} else {
				return false;
			}
		} else {
			prQuadInternal node = (prQuadInternal) child;
			boolean success;
			Direction result = elem.directionFrom( xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ) ); // get direction from the origin
			if( result == Direction.SW ) {
				success = this.delete( node, node.SW, Direction.SW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				success = this.delete( node, node.SE, Direction.SE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				success = this.delete( node, node.NW, Direction.NW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				success = this.delete( node, node.NE, Direction.NE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
			// if all or all but one the leaves of the internal node are null, retract the node
			tryRetractNode( parent, node, directionFrom );
			return success;
		}
	}
	
	private void tryRetractNode( prQuadInternal parent, prQuadInternal node, Direction directionFrom ) {
		if( allLeavesAreNull( node ) ) {
			setParentLeavesNull( parent, directionFrom );
		} else if( node.SW != null && threeLeavesAreNull( node.SE, node.NW, node.NE ) && node.SW.getClass().getName().equals( this.leafClassName ) ) {
			IPoint element = ( (prQuadLeaf) node.SW ).Elements.get(0);
			setParentLeavesNull( parent, directionFrom );
			for( T index : element.)
			insert( element );
		} else if( node.SE != null && threeLeavesAreNull( node.SW, node.NW, node.NE ) && node.SE.getClass().getName().equals( this.leafClassName ) ) {
			IPoint element = ( (prQuadLeaf) node.SE ).Elements.get(0);
			setParentLeavesNull( parent, directionFrom );
			insert( element );
		} else if( node.NE != null && threeLeavesAreNull( node.SW, node.SE, node.NW ) && node.NE.getClass().getName().equals( this.leafClassName ) ) {
			IPoint element = ( (prQuadLeaf) node.NE ).Elements.get(0);
			setParentLeavesNull( parent, directionFrom );
			insert( element );
		} else if( node.NW != null && threeLeavesAreNull( node.SW, node.NE, node.SE ) && node.NW.getClass().getName().equals( this.leafClassName ) ) {
			IPoint element = ( (prQuadLeaf) node.NW ).Elements.get(0);
			setParentLeavesNull( parent, directionFrom );
			insert( element );
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
	
	// Pre:  elem != null 
	// Returns reference to an element x within the tree such that  
	// elem.equals(x)is true, provided such a matching element occurs within 
	// the tree; returns null otherwise. 
	public IPoint find(IPoint Elem) { 
		return this.find( this.root, Elem, this.xMin, this.xMax, this.yMin, this.yMax );
	}
	
	private IPoint find( prQuadNode root, IPoint elem, long xMin, long xMax, long yMin, long yMax ) {
		// case not in bounds
		if( elem.inQuadrant( xMin, xMax, yMin, yMax ) == Direction.NOQUADRANT ) {
			return null;
		}
		// case this is an empty leaf node
		if( root == null ) {
			return null;
		}
		
		// case there is a data point in the leaf
		if( root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) root;
			if( leaf.Elements.get( 0 ).equals( elem ) ) {
				return leaf.Elements.get( 0 );
			} else {
				return null;
			}
		}
		
		// case this is an internal node
		else {
			prQuadInternal node = (prQuadInternal) root;
			Direction result = elem.directionFrom( xMax / 2, yMax / 2 ); // get direction from the origin
			if( result == Direction.SW ) {
				return this.find( node.SW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.SE ) {
				return this.find( node.SE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
			} else if( result == Direction.NW ) {
				return this.find( node.NW, elem, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
			} else {
				return this.find( node.NE, elem, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}
	
	   // Pre:  xLo, xHi, yLo and yHi define a rectangular region 
	   // Returns a collection of (references to) all elements x such that x is  
	   //in the tree and x lies at coordinates within the defined rectangular  
	   // region, including the boundary of the region.
	public Vector<IPoint> find(long xLo, long xHi, long yLo, long yHi) {
		Vector<IPoint> elements = new Vector<IPoint>();
		find( elements, this.root, xLo, xHi, yLo, yHi, this.xMin, this.xMax, this.yMin, this.yMax );
		
		return elements;
   }
   
	private void find( Vector<IPoint> elements, prQuadNode root, long xLo, long xHi, long yLo, long yHi,
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
				for( IPoint element : leaf.Elements ) {
					if( element.inQuadrant( xLo, xHi, yLo, yHi ) != Direction.NOQUADRANT ) {
						elements.add( element );
					}
				}
			} else {
				prQuadInternal internal = (prQuadInternal) root;
				find( elements, internal.SW, xLo, xHi, yLo, yHi, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin, yMin + ( ( yMax - yMin ) / 2 ) );
				find( elements, internal.SE, xLo, xHi, yLo, yHi, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin, yMin + ( ( yMax - yMin ) / 2 ) );
				find( elements, internal.NW, xLo, xHi, yLo, yHi, xMin, xMin + ( ( xMax - xMin ) / 2 ), yMin + ( ( yMax - yMin ) / 2 ), yMax );
				find( elements, internal.NE, xLo, xHi, yLo, yHi, xMin + ( ( xMax - xMin ) / 2 ), xMax, yMin + ( ( yMax - yMin ) / 2 ), yMax );
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		appendSubtree( this.root, sb, "" );
		return sb.toString();
	}
	
	private void appendSubtree( prQuadNode root, StringBuilder sb, String padding ) {
		// empty leaf = *
		// internal node = []
		String extraPadding = "   ";
		if( root == null ) {
			sb.append( padding + "*" );
		} else if( root.getClass().getName().equals( this.leafClassName ) ) {
			prQuadLeaf leaf = (prQuadLeaf) root;
			sb.append( padding + leaf.Elements.get( 0 ).toString() );
		} else {
			prQuadInternal node = (prQuadInternal) root;
			appendSubtree( node.NE, sb, padding + extraPadding );
			sb.append( "\n" );
			appendSubtree( node.NW, sb, padding + extraPadding );
			sb.append( "\n" );
			sb.append( padding + "[]" );
			sb.append( "\n" );
			appendSubtree( node.SW, sb, padding + extraPadding );
			sb.append( "\n" );
			appendSubtree( node.SE, sb, padding + extraPadding );
		}
	}
}
