package com.example.newchinesechess;


public class PCNextMove_1 {
	//红方走棋 side=1 黑方 side =0
	//public static void main(String[] args)
	public QZMove Go(int Qizi[][],int side,int Qzcount[]) 
	{
		int depth=0,Sum;
		QZMove t = new QZMove();//搜索得到的下一步走法
		QP qp = new QP(Qizi,Qzcount);
		//qp.printqp();
		//qp.printhash();
		Sum=qp.GetSum();
		/*
		if(Sum>=16) depth=4;
		else if(Sum<16&&Sum>=8) depth=5;
		else depth=6;
		*/
		depth=2;
		GetNextMove g=new GetNextMove(qp,depth,Qzcount);
		t=g.run(qp,side);//红方走棋 side=1 黑方 side =0
		//System.out.print(t.y_now+","+t.x_now+"->"+t.y_to+","+t.x_to);
		int temp;
		temp=t.y_now;t.y_now=t.x_now;t.x_now=temp;
		temp=t.y_to;t.y_to=t.x_to;t.x_to=temp;
		return t;
	}
}
class QZMove
{
	public int x_now=0,y_now=0;
	public int x_to=0,y_to=0;
	public boolean IsMove;
	public QZMove()
	{
		IsMove=false;
	}
	public void SetNowYandX(int Y,int X)
	{
		this.y_now=Y;
		this.x_now=X;
	}
	public void SetToYandX(int Y,int X)
	{
		this.y_to=Y;
		this.x_to=X;
	}
	public void print()
	{
		System.out.println(y_now+" "+x_now+"->"+y_to+" "+x_to);
	}
}
class QP
{
	public int qzcount[] = new int[20];
	public int Sum;
	public int map[][] = new int[10][10];
	public final char ToCh[] = new char[]{
			' ','帅','车','马','炮','士','相','兵','将','车','马','炮','仕','象','卒'
	};
	public int hash[][] = new int[20][10];
	public QP(int temp[][],int Qzcount[])//构造函数
	{
		Sum=0;
		for(int i=1;i<=14;i++) qzcount[i]=Qzcount[i];
		for(int i=1;i<=14;i++)
		{
			for(int k=0;k<=8;k++) hash[i][k]=-1;
		}
		for(int i=0;i<10;i++)//复制
		{
			for(int k=0;k<9;k++) 
			{
				map[i][k]=temp[i][k];
				if(map[i][k]!=0)
				{
					Sum++;
					int num=0;
					while(hash[map[i][k]][num]!=-1)
					{
						num++;
					}
					hash[map[i][k]][num]=i*10+k;
				}
			}
		}		
	}
	public void printqp()//输出当前棋盘局面
	{
		for(int i=0;i<10;i++)
		{
			for(int k=0;k<9;k++)
			{
				if(map[i][k]==0) System.out.print("     ");
				else System.out.print(ToCh[map[i][k]]);
			}
			System.out.println();
		}
	}
	public void printhash()//输出当前hssh
	{
		System.out.println("hash表");
		for(int i=1;i<=14;i++)
		{
			System.out.print(ToCh[i]+":");
			int n=qzcount[i]-1;
			for(int k=0;k<=n;k++)
				System.out.print(hash[i][k]+" ");
			System.out.println();
		}
	}
	public int GetSum()
	{
		return Sum;
	}
}
class GetNextMove
{
	int Depth;//向下搜的深度
	int Rscore,Bscore;//红黑方当前分数
	static final int Red_side=1,Black_side=0,Qzadd=1,Qzdelete=0;
	int exceed[]=new int[20];
	public final int Qzscore[]=new int[]{
			0,1000,10,5,6,2,2,1,1000,10,5,6,2,2,1
	};
	//帅1 车2  马3  炮 4 士 5  相6  兵 7  黑
	public final int move[][][]=new int [][][]{
			{
				{0,0}
			},
			{
				{0,0},{1,0},{0,1},{-1,0},{0,-1}//帅1
			},
			{
				{0,0},//车
				{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},{8,0},{9,0},
				{-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0},{-8,0},{-9,0},
				{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},
				{0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},{0,-8}
			},
			{
				{0,0},{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2},//马3
			},
			{
				{0,0},//炮
				{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},{8,0},{9,0},
				{-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0},{-8,0},{-9,0},
				{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},
				{0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},{0,-8}
			},
			{
				{0,0},{1,1},{-1,-1},{1,-1},{-1,1}//士5
			},
			{
				{0,0},{2,2},{-2,-2},{2,-2},{-2,2}//相6
			},
			{
				{0,0},{1,0},{0,1},{-1,0},{0,-1}//兵7
			}
	};
	//QZMove qzmove = new QZMove();
	QZMove ans= new QZMove();
	public GetNextMove(QP qp,int depth,int Qzcount[])//构造函数 初始化
	{
		for(int i=1;i<=14;i++) exceed[i]=Qzcount[i];
		GetBeginScore(qp);
		this.Depth=depth;
	}
	public QZMove run(QP qp,int side)
	{
		SupposeMove(side,qp,Depth);
		return ans;
	}
	// 帅1 车2  马3  炮 4 士 5  相6  兵 7  黑  //帅8 车9  马10 炮 11 士 12 相13   兵 14 红
	public int SupposeMove(int side,QP qp,int depth)//下一步棋
	{
		int qznumber=0,which=0;
		int Maxscore=-20000;
		int endnumber=0,firstnumber=0;
		if(side==Red_side) {
			firstnumber=8;
			endnumber=14;
		}
		else if(side==Black_side)
		{
			firstnumber=1;
			endnumber=7;
		}
		for(qznumber=firstnumber;qznumber<=endnumber;qznumber++)
		{		
			int add=0;	
			for(which=0;;which++)
			{			
				if(IsExceed(qznumber,which)==false) break;
				if(qp.hash[qznumber][which]==-1) continue;
				int Y=qp.hash[qznumber][which]/10;
				int X=qp.hash[qznumber][which]%10;	
				if(qznumber>=8){
					qznumber-=7;
					add=7;
				}
				switch(qznumber)
				{
				//将 帅/////////////////////////////////////////////////////////////////////////////////
					case 1:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,4);
						break;
					}
					//车///////////////////////////////////////////////////////////////////////////
					case 2:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,34);
						break;
					}
					//马////////////////////////////////////////////////////////////////////////////////
					case 3:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,8);
						break;
					}
					//炮////////////////////////////////////////////////////////////////////////////////
					case 4:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,34);
						break;
					}
					//士/////////////////////////////////////////////////////////////////////////////
					case 5:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,4);
						break;
					}
					//相//////////////////////////////////////////////////////////////////
					case 6:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,4);
						break;
					}
					//兵///////////////////////////////////////////////////////////////////////
					case 7:
					{
						Maxscore=Search(depth,qp,Y,X,qznumber,which,add,side,ans,Maxscore,4);
						break;
					}
				}
				qznumber+=add;
			}			
		}
		return Maxscore;
	}
	public int Search(int depth,QP qp,int Y,int X,int qznumber,int which,int add,int side,QZMove ans,int Maxscore,int n)
	{
		int y,x,Maxtemp;
		Rule rule = new Rule();
		for(int i=1;i<=n;i++)
		{
			y=Y+move[qznumber][i][0];//Y X原坐标  y x目标坐标
			x=X+move[qznumber][i][1];
			if(rule.canMove(qp.map,Y,X,y,x)==true)//我的YX和你的相反
			{
				int k=0;
				int temp=qp.map[y][x];//如果是吃子
				qp.map[Y][X]=0;//修改移动棋子原坐标为0
				qp.map[y][x]=qznumber+add;//修改移动棋子目标坐标
				qp.hash[qznumber+add][which]=y*10+x;//修改移动棋子hash值								
				//System.out.println(temp);
				if(temp!=0)//如果目标地有棋子
				{									
					while(qp.hash[temp][k]!=y*10+x)	k++;
					qp.hash[temp][k]=-1;
					Maxtemp=GetNowScore(side,temp,Qzdelete);
					if(depth>1)
					{
						Maxtemp=-SupposeMove(side^1,qp,depth-1);
					}
					if(Maxtemp>Maxscore){
						if(depth==Depth)
						{
							ans.SetNowYandX(Y,X);
							ans.SetToYandX(y,x);
							//System.out.println(GetNowScore(side));
						}
						Maxscore=Maxtemp;
					}
					GetNowScore(side,temp,Qzadd);
					qp.hash[temp][k]=y*10+x;
					qp.map[y][x]=temp;//复位
				}					
				else{
					Maxtemp=GetNowScore(side);
					if(depth>1)
					{
						Maxtemp=-SupposeMove(side^1,qp,depth-1);
					}
					//System.out.println(Maxtemp);
					if(Maxtemp>Maxscore){
						
						if(Depth==depth)
						{	
							ans.SetNowYandX(Y,X);
							ans.SetToYandX(y,x);
							//System.out.println(GetNowScore(side));
						}
						Maxscore=Maxtemp;
					}
					qp.map[y][x]=0;//复位
				}
				qp.map[Y][X]=qznumber+add;//复位
				qp.hash[qznumber+add][which]=Y*10+X;//复位				
			}
		}
		return Maxscore;
	}
	// 帅1 车2  马3  炮 4 士 5  相6  兵 7  黑  //帅8 车9  马10 炮 11 士 12 相13   兵 14 红
	public boolean IsExceed(int qznumber,int which)//是否越界
	{
		if(which>=exceed[qznumber]) return false;
		return true;
	}
	 //1-7 黑 8-14红	//得到初始局面分数
	public void GetBeginScore(QP qp)
	{
		Rscore=Bscore=0;
		for(int i=1;i<=7;i++)
		{
			int n=exceed[i]-1;
			for(int k=0;k<=n;k++)	if(qp.hash[i][k]!=-1) Bscore+=Qzscore[i];
		}
		for(int i=8;i<=14;i++)
		{
			int n=exceed[i]-1;
			for(int k=0;k<=n;k++)	if(qp.hash[i][k]!=-1) Rscore+=Qzscore[i];
		}
		//System.out.println("Rscore="+Rscore+"  Bscore="+Bscore);
	}
	//当前为哪一方 改变的棋子  1-7 黑 8-14红	
	//如果减少的是红方的棋子，则是黑方走棋，返回Bscore-Rscore
	public int GetNowScore(int side,int Qznumber,int AddorDelete)//得到当前局面子力分数
	{
		int Sc=Qzscore[Qznumber],Ans;
		if(Qznumber<=7)
		{
			if(AddorDelete==Qzadd)	Bscore+=Sc;
			else if(AddorDelete==Qzdelete) Bscore-=Sc;
		}
		else
		{
			if(AddorDelete==Qzadd)	Rscore+=Sc;
			else if(AddorDelete==Qzdelete) Rscore-=Sc;		
		}
		if(side==Black_side) Ans=Bscore-Rscore;
		else  Ans=Rscore-Bscore;
		//System.out.println(Rscore+" "+Bscore);
		return Ans;
	}
	public int GetNowScore(int side)//得到当前局面子力分数
	{
		int Ans;
		if(side==Black_side) Ans=Bscore-Rscore;
		else  Ans=Rscore-Bscore;
		return Ans;
	}
}


