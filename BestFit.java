import java.util.*;
import javafx.util.Pair;
import java.io.*;

public class BestFit
{
	bst_B T1;
	bst_cap T2;
	bst_O T3;
    public BestFit()
	{
		T1= new bst_B();
		T2= new bst_cap();
		T3= new bst_O();
    }
	
    public void add_bin(int bin_id, int capacity)
	{
		try
		{
			nodeBId p=new nodeBId();
			nodeBCap t=new nodeBCap();
			p.setBId(bin_id);
			p.setpointer(t);
			t.setCap(capacity);
			t.setpointer(p);
			T1.setRoot(T1.add(p, T1.getRoot()));
			T2.setRoot(T2.add(t, T2.getRoot()));
		}
		catch(CustomException f)
		{
			System.out.println(f);
			System.exit(0);
		}
	}

    public int add_object(int obj_id, int size)
	{
		try
		{
			nodeBCap t=T2.getLargestBin(T2.getRoot());
			nodeOId p=new nodeOId();
			p.setOId(obj_id);
			p.setSize(size);
			p.setBin(t);
			if(t.getkey()>=size)
			{
				T3.setRoot(T3.add(p,T3.getRoot()));
				T2.setRoot(T2.delete(t,T2.getRoot()));
				t.setCap(t.getkey()-size);
				t.addObj(obj_id,size);
				t.setleft(null);
				t.setright(null);
				t.setparent(null);
				T2.setRoot(T2.add(t, T2.getRoot()));
				nodeBId t_=t.getpointer();
				return t_.getkey();
			}
			else
			{
				return obj_id;
			}
		}
		catch(CustomException f)
		{
			System.out.println(f);
			System.exit(0);
		}
		return 0;
	}

    public int delete_object(int obj_id)
	{	
		try
		{
			nodeOId temp=T3.search(obj_id, T3.getRoot());
			nodeOId p=new nodeOId();
			p.setOId(temp.getkey());
			p.setSize(temp.getSize());
			p.setBin(temp.getBin());
			T3.setRoot(T3.delete(obj_id,T3.getRoot()));
			nodeBCap t=p.getBin();
			T2.setRoot(T2.delete(t,T2.getRoot()));	
			t.setCap(t.getkey()+p.getSize());
			t.deleteObj(p.getkey(),p.getSize());
			t.setleft(null);
			t.setright(null);
			t.setparent(null);
			T2.setRoot(T2.add(t, T2.getRoot()));
			nodeBId t_=t.getpointer();
			return t_.getkey();
			
		}
		catch(CustomException f)
		{
			System.out.println(f);
			System.exit(0);
		}
		return 0;
	}

    public List<Pair<Integer, Integer>> contents(int bin_id)
	{
		try
		{
			nodeBId p=T1.search(bin_id,T1.getRoot());
			nodeBCap t=p.getpointer();
			return t.getList();
		}
		catch(CustomException f)
		{
			System.out.println(f);
			System.exit(0);
		}
		return null;
    }
   
   public static void main(String args[])
   {
	   try
	   {
		   
			String inputfileName = args[0];
			FileInputStream fstream = new FileInputStream(inputfileName);
			Scanner s = new Scanner(fstream);
			
			//Scanner s = new Scanner(new File(args[0]));
			BestFit BF=new BestFit();
			while(s.hasNextLine())
			{
				String[] stri=s.nextLine().split(" ");
				int op=Integer.parseInt(stri[0]);
				if(op==1)
				{
					int a=Integer.parseInt(stri[1]);
					int b=Integer.parseInt(stri[2]);
					BF.add_bin(a,b);
				}
				else if(op==2)
				{
					int a=Integer.parseInt(stri[1]);
					int b=Integer.parseInt(stri[2]);
					System.out.println(BF.add_object(a,b));
				}
				else if(op==3)
				{
					int a=Integer.parseInt(stri[1]);
					System.out.println(BF.delete_object(a));
				}	
				else
				{
					int a=Integer.parseInt(stri[1]);
					List<Pair<Integer,Integer>> seq=BF.contents(a);
					for(int i=0;i<seq.size();i++)
					{
						System.out.println(seq.get(i).getKey()+" "+seq.get(i).getValue());
					}
				}
			}
			
	    }
		catch (FileNotFoundException e) 
		{ 
			System.out.println("File not found");
		} 
		
			
   }
}
class nodeBId
{
	private int BId;
	private nodeBCap pointer;
	private nodeBId parent;
	private nodeBId left;
	private nodeBId right;
	private int h;
	
	public nodeBId()
	{
		h=1;
	}
	
	
	public void setHeight(int a)
	{
		h=a;
	}
	
	public int getHeight()
	{
		return h;
	}
	
	public int getkey()
	{
		return BId;
	}
	
	public nodeBCap getpointer()
	{
		return pointer;
	}
	
	public void setBId(int id)
	{
		BId=id;
	}
	
	public void setpointer(nodeBCap p)
	{
		pointer=p;
	}
	
	public void setleft(nodeBId l)
	{
		left=l;
		if(l!=null)
		{
			l.setparent(this);
		}
	}	
	
	public void setright(nodeBId r)
	{
		right=r;
		if(r!=null)
		{
			r.setparent(this);
		}
	}	
	
	public void setparent(nodeBId p)
	{
		parent=p;
	}

	public nodeBId getLeft()
	{
		return left;
	}
	
	public nodeBId getRight()
	{
		return right;
	}
	
	public nodeBId getParent()
	{
		return parent;
	}
	
}


class nodeBCap
{
	private int BCap;
	private nodeBId pointer;
	private nodeBCap parent;
	private nodeBCap left;
	private nodeBCap right;
	private List<Pair<Integer,Integer>> objList;
	private int h;
	
	public nodeBCap()
	{
		h=1;
		objList=new ArrayList<Pair<Integer,Integer>>();
	}
	
	public void setHeight(int a)
	{
		h=a;
	}
	
	public int getHeight()
	{
		return h;
	}


	public void setList(List<Pair<Integer,Integer>> L)
	{
		objList=L;
	}
		
	public int getkey()
	{
		return BCap;
	}
	
	public nodeBId getpointer()
	{
		return pointer;
	}
	
	public void setCap(int cap)
	{
		BCap=cap;
	}
	
	public void setpointer(nodeBId p)
	{
		pointer=p;
	}
	
	public List<Pair<Integer,Integer>> getList()
	{
		return objList;
	}
	
	public void addObj(int Id, int Size)
	{
		Integer OId=new Integer(Id);
		Integer size=new Integer(Size);
		Pair <Integer, Integer> p =  new Pair <>(OId, size);
		objList.add(p);
	}
	
	public void deleteObj(int Id, int Size)
	{
		Integer OId=new Integer(Id);
		Integer size=new Integer(Size);
		Pair <Integer, Integer> p =  new Pair <>(OId, size);
		objList.remove(p);
	}
	
	public void setleft(nodeBCap l)
	{
		left=l;
		if(l!=null)
		{
			l.setparent(this);
		}
	}	
	
	public void setright(nodeBCap r)
	{
		right=r;
		if(r!=null)
		{
			r.setparent(this);
		}	
	}
	
	public void setparent(nodeBCap p)
	{
		parent=p;
	}

	public nodeBCap getLeft()
	{
		return left;
	}
	
	public nodeBCap getRight()
	{
		return right;
	}
	
	public nodeBCap getParent()
	{
		return parent;
	}
}

class nodeOId
{
	private int OId;
	private int size;
	private nodeBCap pointer;
	private nodeOId parent;
	private nodeOId left;
	private nodeOId right;
	private int h;
	
	public nodeOId()
	{
		h=1;
	}
	
	public void setHeight(int a)
	{
		h=a;
	}
	
	public int getHeight()
	{
		return h;
	}
	
	public int getkey()
	{
		return OId;
	}
	
	public nodeBCap getBin()
	{
		return pointer;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setOId(int id)
	{
		OId=id;
	}
	
	public void setSize(int Size)
	{
		size=Size;
	}
	
	public void setBin(nodeBCap p)
	{
		pointer=p;
	}
	
	public void setleft(nodeOId l)
	{
		left=l;
		if(l!=null)
		{
			l.setparent(this);
		}
	}	
	
	public void setright(nodeOId r)
	{
		right=r;
		if(r!=null)
		{
			r.setparent(this);
		}
	}	
	
	public void setparent(nodeOId p)
	{
		parent=p;
	}

	public nodeOId getLeft()
	{
		return left;
	}
	
	public nodeOId getRight()
	{
		return right;
	}
	
	public nodeOId getParent()
	{
		return parent;
	}
	
}


class CustomException extends Exception
{
	String error;
	public CustomException(String message)
	{
		error=message;
	}
	public String toString()
	{
		return error; 
	}
}

class bst_B
{
	private nodeBId root;
	
	public bst_B()
	{
		
	}
	
	public nodeBId getRoot()
	{
		return root;
	}
	
	private int height(nodeBId N)
	{
		if(N==null)
		{			
            return 0;  
		}
		return N.getHeight(); 
	}

	public void setRoot(nodeBId N)
	{
		root=N;
	}
	

	public nodeBId add(nodeBId node, nodeBId bst_root) throws CustomException 
    {  
        
        if(bst_root==null)  
		{
            return node;  
		}
		
        if(node.getkey()<bst_root.getkey()) 
		{			
            bst_root.setleft(add(node,bst_root.getLeft()));  
		}
        else if(node.getkey()>bst_root.getkey())
		{			
            bst_root.setright(add(node,bst_root.getRight()));    
		}
        else
		{	
			throw new CustomException("It already exists");
		}
		
		bst_root.setHeight(Math.max(height(bst_root.getLeft()),height(bst_root.getRight()))+1);
        
		int balance = getBalance(bst_root);  
 
		
					
			if(balance>1 && node.getkey()< bst_root.getLeft().getkey())  
			{
				return rightRotate(bst_root);  
			}
			
			
	        if(balance>1 && node.getkey()>bst_root.getLeft().getkey())  
			{  
				bst_root.setleft(leftRotate(bst_root.getLeft()));  
				return rightRotate(bst_root);  
			} 
							
		
		
			
			if(balance<-1 && node.getkey()>bst_root.getRight().getkey())  
			{
				return leftRotate(bst_root);  
			}
			
			
			
	        if(balance<-1 && node.getkey()<bst_root.getRight().getkey())  
			{  
				bst_root.setright(rightRotate(bst_root.getRight()));  
				return leftRotate(bst_root);  
			}  
			
		
		
         return bst_root;  
    } 
	
	
	
	private int getBalance(nodeBId N)  
    {  
        if(N==null)
		{			
            return 0;
		}
        return height(N.getLeft())-height(N.getRight());  
    }
	
	private nodeBId rightRotate(nodeBId y)  
    {  
        nodeBId x=y.getLeft();  
        nodeBId p2= x.getRight();    
        x.setright(y);  
        y.setleft(p2); 
        y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
	    return x;  
    }  
  
    private nodeBId leftRotate(nodeBId x)  
    {  
		nodeBId y=x.getRight();  
        nodeBId p2= y.getLeft();    
        y.setleft(x); 
		x.setright(p2);
		x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
		y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        return y;  
    } 

	
	public nodeBId search(int key, nodeBId bst_root) throws CustomException
	{
		if(bst_root==null)
		{
			throw new CustomException("Tree is empty");
		}
		else if(bst_root.getkey()>key)
		{
			if(bst_root.getLeft()==null)
			{
				throw new CustomException("It doesn't exist");
			}
			return search(key,bst_root.getLeft());
		}
		else if(bst_root.getkey()<key)
		{
			if(bst_root.getRight()==null)
			{
				throw new CustomException("It doesn't exist");
			}
			return search(key,bst_root.getRight());
		}
		else
		{
			return bst_root;
		}
	}
	
	
}

class bst_O
{
	private nodeOId root;
	
	public bst_O()
	{
		
	}
	
	public nodeOId getRoot()
	{
		return root;
	}
	
	private int height(nodeOId N)
	{
		if(N==null)
		{			
            return 0;  
		}
		return N.getHeight(); 
	}

	public void setRoot(nodeOId N)
	{
		root=N;
	}
	

	public nodeOId add(nodeOId node, nodeOId bst_root) throws CustomException 
    {  
        
        if(bst_root==null)  
		{
            return node;  
		}
		
        if(node.getkey()<bst_root.getkey()) 
		{			
            bst_root.setleft(add(node,bst_root.getLeft()));  
		}
        else if(node.getkey()>bst_root.getkey())
		{			
            bst_root.setright(add(node,bst_root.getRight()));    
		}
        else
		{	
			throw new CustomException("It already exists");
		}
		
		bst_root.setHeight(Math.max(height(bst_root.getLeft()),height(bst_root.getRight()))+1);
        
		int balance = getBalance(bst_root);  
 
		
					
			if(balance>1 && node.getkey()< bst_root.getLeft().getkey())  
			{
				return rightRotate(bst_root);  
			}
			
			
	        if(balance>1 && node.getkey()>bst_root.getLeft().getkey())  
			{  
				bst_root.setleft(leftRotate(bst_root.getLeft()));  
				return rightRotate(bst_root);  
			} 
							
		
		
			
			if(balance<-1 && node.getkey()>bst_root.getRight().getkey())  
			{
				return leftRotate(bst_root);  
			}
			
			
			
	        if(balance<-1 && node.getkey()<bst_root.getRight().getkey())  
			{  
				bst_root.setright(rightRotate(bst_root.getRight()));  
				return leftRotate(bst_root);  
			}  
			
		
		
         return bst_root;  
    } 
	
	
	
	private int getBalance(nodeOId N)  
    {  
        if(N==null)
		{			
            return 0;
		}
        return height(N.getLeft())-height(N.getRight());  
    }
	
	private nodeOId rightRotate(nodeOId y)  
    {  
        nodeOId x=y.getLeft();  
        nodeOId p2= x.getRight();    
        x.setright(y);  
        y.setleft(p2); 
        y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
	    return x;  
    }  
  
    private nodeOId leftRotate(nodeOId x)  
    {  
		nodeOId y=x.getRight();  
        nodeOId p2= y.getLeft();    
        y.setleft(x); 
		x.setright(p2);
		x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
		y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        return y;  
    } 
		
	public nodeOId search(int key, nodeOId bst_root) throws CustomException
	{
		if(bst_root==null)
		{
			throw new CustomException("Tree is empty");
		}
		else if(bst_root.getkey()>key)
		{
			if(bst_root.getLeft()==null)
			{
				throw new CustomException("It doesn't exist");
			}
			return search(key,bst_root.getLeft());
		}
		else if(bst_root.getkey()<key)
		{
			if(bst_root.getRight()==null)
			{
				throw new CustomException("It doesn't exist");
			}
			return search(key,bst_root.getRight());
		}
		else
		{
			return bst_root;
		}
	}
	
	public nodeOId delete(int key,nodeOId bst_root)
	{
		if(bst_root==null)
		{
			return bst_root;
		}
		
		if(key<bst_root.getkey())
		{
			bst_root.setleft(delete(key,bst_root.getLeft()));
		}
		else if(key>bst_root.getkey())
		{
			bst_root.setright(delete(key,bst_root.getRight()));
		}
		else
		{
			if(bst_root.getLeft()==null && bst_root.getRight()==null)
			{
				bst_root=null;
			}
			else if(bst_root.getLeft()==null)
			{
				nodeOId t=bst_root.getParent();
				bst_root=bst_root.getRight();
				if(t==null)
				{
					bst_root.setparent(null);
				}
			}
			else if(bst_root.getRight()==null)
			{
				nodeOId t=bst_root.getParent();
				bst_root=bst_root.getLeft();
				if(t==null)
				{
					bst_root.setparent(null);
				}
			}
			else 
            {
				
				nodeOId temp=minOnRoot(bst_root.getRight());
				bst_root.setOId(temp.getkey());
				bst_root.setSize(temp.getSize());
				bst_root.setBin(temp.getBin());
				bst_root.setright(delete(temp.getkey(),bst_root.getRight()));
            }  
		
		}
		
		if(bst_root == null)
		{			
            return bst_root; 
		}
		
  
        bst_root.setHeight(Math.max(height(bst_root.getLeft()),height(bst_root.getRight()))+1);
		int balance = getBalance(bst_root);
  
        if(balance>1 && getBalance(bst_root.getLeft())>=0)  
		{
            return rightRotate(bst_root);  
		}
        else if(balance>1 && getBalance(bst_root.getLeft())<0)  
        {  
            bst_root.setleft(leftRotate(bst_root.getLeft()));  
            return rightRotate(bst_root);  
        }  
        else if(balance<-1 && getBalance(bst_root.getRight())<=0) 
		{			
            return leftRotate(bst_root);  
		}   
        else if(balance<-1 && getBalance(bst_root.getRight())>0)  
        {  
            bst_root.setright(rightRotate(bst_root.getRight()));  
            return leftRotate(bst_root);  
        }  
  
        return bst_root;
		
	}
	
	
	
	private nodeOId minOnRoot(nodeOId node)
	{
		if(node.getLeft()==null)
		{
			return node;
		}
		else
		{
			return minOnRoot(node.getLeft());
		}
	}
	
}

class bst_cap
{
	private nodeBCap root;
	
	public bst_cap()
	{
		
	}
	
	
	public nodeBCap getRoot()
	{
		return root;
	}
	
	public void setRoot(nodeBCap N)
	{
		root=N;
	}
	
	private int height(nodeBCap N)
	{
		if(N==null)
		{			
            return 0;  
		}
		return N.getHeight(); 
	}
	
	public nodeBCap add(nodeBCap node, nodeBCap bst_root)  
    {  
        
        if(bst_root==null)  
		{
            return node;  
		}
		
        if(node.getkey()<bst_root.getkey()) 
		{			
            bst_root.setleft(add(node,bst_root.getLeft()));  
		}
        else if(node.getkey()>bst_root.getkey())
		{			
            bst_root.setright(add(node,bst_root.getRight()));    
		}
        else
		{	
			nodeBId p=bst_root.getpointer();
			nodeBId t=node.getpointer();
			if(t.getkey()<p.getkey())
			{
				bst_root.setleft(add(node,bst_root.getLeft()));
			}
			else if(t.getkey()>p.getkey())
			{
				bst_root.setright(add(node,bst_root.getRight()));
			}
			else
			{
				//return bst_root;  
			}
		}
		
		bst_root.setHeight(Math.max(height(bst_root.getLeft()),height(bst_root.getRight()))+1);
        
		int balance = getBalance(bst_root);  
		nodeBCap pie=new nodeBCap();
 
		
		if(balance>1 && getBalance(bst_root.getLeft())>=0)  
		{
			pie=rightRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;  
		}
        if(balance>1 && getBalance(bst_root.getLeft())<0)  
        {  
            bst_root.setleft(leftRotate(bst_root.getLeft()));  
			pie=rightRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
        }  
        if(balance<-1 && getBalance(bst_root.getRight())<=0) 
		{			
			pie=leftRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
		}   
        if(balance<-1 && getBalance(bst_root.getRight())>0)  
        {  
            bst_root.setright(rightRotate(bst_root.getRight()));  
			pie=leftRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
        }  
		
		return bst_root;  
    
	} 
	
	
	
	public int getBalance(nodeBCap N)  
    {  
        if(N==null)
		{			
            return 0;
		}
        return height(N.getLeft())-height(N.getRight());  
    }
	
	private nodeBCap rightRotate(nodeBCap y)  
    {  
        nodeBCap x=y.getLeft();  
        nodeBCap p2= x.getRight();    
		if(y.getParent()==null)
		{
			x.setparent(null);
		} 
        x.setright(y);  
        y.setleft(p2); 
        y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
	    return x;  
    }  
  
    private nodeBCap leftRotate(nodeBCap x)  
    {  
		
		nodeBCap y=x.getRight();  
        nodeBCap p2= y.getLeft();    
        if(x.getParent()==null)
		{
			y.setparent(null);
		}                                                      
		y.setleft(x); 
		x.setright(p2);
		x.setHeight(Math.max(height(x.getLeft()),height(x.getRight()))+1);
		y.setHeight(Math.max(height(y.getLeft()),height(y.getRight()))+1);  
        return y;  
    } 
	
	public nodeBCap delete(nodeBCap node,nodeBCap bst_root)
	{
		if(bst_root==null)
		{
			return bst_root;
		}
		
		if(node.getkey()<bst_root.getkey())
		{
			bst_root.setleft(delete(node,bst_root.getLeft()));
		}
		else if(node.getkey()>bst_root.getkey())
		{
			bst_root.setright(delete(node,bst_root.getRight()));
		}
		else
		{
			nodeBId pi=node.getpointer();
			nodeBId ti=bst_root.getpointer();
			if(pi.getkey()<ti.getkey())
			{
				bst_root.setleft(delete(node,bst_root.getLeft()));
			}
			else if(pi.getkey()>ti.getkey())
			{
				bst_root.setright(delete(node,bst_root.getRight()));
			}
			else
			{
				if(bst_root.getLeft()==null && bst_root.getRight()==null)
				{
					bst_root=null;
				}
				else if(bst_root.getLeft()==null)
				{
					nodeBCap t=bst_root.getParent();
					bst_root=bst_root.getRight();
					if(t==null)
					{
						bst_root.setparent(null);
					}
				}
				else if(bst_root.getRight()==null)
				{
					nodeBCap t=bst_root.getParent();
					bst_root=bst_root.getLeft();
					if(t==null)
					{
						bst_root.setparent(null);
					}
				}
				else 
				{
					
					nodeBCap temp=minOnRoot(bst_root.getRight());
					bst_root.setright(delete(temp,bst_root.getRight()));
					
					temp.setright(bst_root.getRight());
					temp.setleft(bst_root.getLeft());
					nodeBCap temp1=bst_root.getParent();
					if(temp1.getLeft()==bst_root)
					{
						temp1.setleft(temp);
					}
					else
					{
						temp1.setright(temp);
					}
					bst_root=temp;
					
				}
					
			}		
		}
		
		if(bst_root == null)
		{			
            return bst_root; 
		}
		
  
        bst_root.setHeight(Math.max(height(bst_root.getLeft()),height(bst_root.getRight()))+1);
		int balance = getBalance(bst_root);
		nodeBCap pie=new nodeBCap();
  
        if(balance>1 && getBalance(bst_root.getLeft())>=0)  
		{
			pie=rightRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;  
		}
        if(balance>1 && getBalance(bst_root.getLeft())<0)  
        {  
            bst_root.setleft(leftRotate(bst_root.getLeft()));  
			pie=rightRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
        }  
        if(balance<-1 && getBalance(bst_root.getRight())<=0) 
		{			
			pie=leftRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
		}   
        if(balance<-1 && getBalance(bst_root.getRight())>0)  
        {  
            bst_root.setright(rightRotate(bst_root.getRight()));  
			pie=leftRotate(bst_root);
			//System.out.println("balance is (del) "+getBalance(pie));
            return pie;    
        }  
  
        return bst_root;
		
	}
	
	
	private nodeBCap minOnRoot(nodeBCap node)
	{
		if(node.getLeft()==null)
		{
			return node;
		}
		else
		{
			return minOnRoot(node.getLeft());
		}
	}
	
	public nodeBCap getLargestBin(nodeBCap bst_root) throws CustomException
	{
		if(bst_root==null)
		{
			throw new CustomException("no bin present");
		}
		else if(bst_root.getRight()==null)
		{
			return bst_root;
		}
		else
		{
			return getLargestBin(bst_root.getRight());
		}
	}
	
	
}