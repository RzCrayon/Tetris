#Ari Kralios
import pygame, sys, math, random

#initialize game engine
pygame.init()

# open and set a window size 
w = 529
h = 806
surface = pygame.display.set_mode((w,h))

#set title bar 
pygame.display.set_caption("")

#colors here
BLACK = ( 0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 175, 0)
BLUE = (0, 0, 255)
LTGREEN = (67, 189, 56)
CYAN = (89, 212, 234)
YELLOW = (255, 255, 0)
GREY = (40,40,40)
RED = (255,0,0) 
ORANGE = (255, 92, 0)
PURPLE = (178, 38, 255)
LTBLUE = (173, 216, 230)

#add clock to control timing for animation
clock = pygame.time.Clock()


##################   FUNCTIONS   ####################################

def selectRandomBlock(x, y):
    blocks = [[pygame.Rect(x-30, y, 30, 30), pygame.Rect(x-30, y+30, 30, 30), pygame.Rect(x, y, 30, 30), pygame.Rect(x, y+30, 30, 30)], [pygame.Rect(x-30, y, 30, 30), pygame.Rect(x-30, y+30, 30, 30), pygame.Rect(x-30, y+60, 30, 30), pygame.Rect(x, y+60, 30, 30)], [pygame.Rect(x, y, 30, 30), pygame.Rect(x, y+30, 30, 30), pygame.Rect(x, y+60, 30, 30), pygame.Rect(x-30, y+60, 30, 30)], [pygame.Rect(x-30, y, 30, 30), pygame.Rect(x, y, 30, 30), pygame.Rect(x+30, y, 30, 30), pygame.Rect(x, y+30, 30, 30)], [pygame.Rect(x, y, 30, 30), pygame.Rect(x, y+30, 30, 30), pygame.Rect(x-30, y+30, 30, 30), pygame.Rect(x-30, y+60, 30, 30)], [pygame.Rect(x, y, 30, 30), pygame.Rect(x, y+30, 30, 30), pygame.Rect(x+30, y+30, 30, 30), pygame.Rect(x+30, y+60, 30, 30)], [pygame.Rect(x-60, y, 30, 30), pygame.Rect(x-30, y, 30, 30), pygame.Rect(x, y, 30, 30), pygame.Rect(x+30, y, 30, 30)]]
    blockColors = [YELLOW, ORANGE, BLUE, PURPLE, RED, LTGREEN, LTBLUE]       
    block = random.choice(blocks)
    color = blockColors[blocks.index(block)]
    numList = []
    for i in range(len(block)):
        if block[i] == pygame.Rect(x-30, y, 30, 30):
            numList.append(1)
        if block[i] == pygame.Rect(x, y, 30, 30):
            numList.append(2)
        if block[i] == pygame.Rect(x+30, y, 30, 30):
            numList.append(3)
        if block[i] == pygame.Rect(x-30, y+30, 30, 30):
            numList.append(4)
        if block[i] == pygame.Rect(x, y+30, 30, 30):
            numList.append(5)
        if block[i] == pygame.Rect(x+30, y+30, 30, 30):
            numList.append(6)
        if block[i] == pygame.Rect(x-30, y+60, 30, 30):
            numList.append(7)
        if block[i] == pygame.Rect(x, y+60, 30, 30):
            numList.append(8)
        if block[i] == pygame.Rect(x+30, y+60, 30, 30):
            numList.append(9)
        if block[i] == pygame.Rect(x-60, y, 30, 30):
            numList.append(10)
    return block, color, numList

def calculateIndeces(block):
    heightVariable, widthVariable, heightIndex, widthIndex = pygame.Rect(0, 0, 0, 0), pygame.Rect(w, 0, 0, 0), 0, 0
    for i in range(len(block)):
        if block[i].y>heightVariable.y:
            heightVariable = block[i]  
        if block[i].x<widthVariable.x:
            widthVariable = block[i]
    heightIndex = block.index(heightVariable)
    widthIndex = block.index(widthVariable)
    return heightIndex, widthIndex

def moveBlockDown(block, heightIndex):
    for i in range(len(block)):
        if block[heightIndex].y+30<h:
            block[i].y+=1
    

def moveX_and_Y(y, heightIndex, block):
    if block[heightIndex].y+30<h:
        y+=1
    return y

def generateReferenceList(x, y):
    referenceList = [pygame.Rect(x-30, y, 30, 30), pygame.Rect(x, y, 30, 30), pygame.Rect(x+30, y, 30, 30), pygame.Rect(x-30, y+30, 30, 30), pygame.Rect(x, y+30, 30, 30), pygame.Rect(x+30, y+30, 30, 30), pygame.Rect(x-30, y+60, 30, 30), pygame.Rect(x, y+60, 30, 30), pygame.Rect(x+30, y+60, 30, 30), pygame.Rect(x-60, y, 30, 30), pygame.Rect(x-30, y-30, 30, 30), pygame.Rect(x+60, y+60, 30, 30), pygame.Rect(x+30, y+90, 30, 30)]
    return referenceList   

def rotateBlock(block, color, rotate, numList, possiblePositions):
    if rotate == 0:
        for i in range(len(numList)):
            if numList[i] == 1:
                block[i] = possiblePositions[0]
            if numList[i] == 2:
                block[i] = possiblePositions[1]
            if numList[i] == 3:
                block[i] = possiblePositions[2]
            if numList[i] == 4:
                block[i] = possiblePositions[3]
            if numList[i] == 5:
                block[i] = possiblePositions[4]
            if numList[i] == 6:
                block[i] = possiblePositions[5]
            if numList[i] == 7:
                block[i] = possiblePositions[6]
            if numList[i] == 8:
                block[i] = possiblePositions[7]
            if numList[i] == 9:
                block[i] = possiblePositions[8]
            if numList[i] == 10:
                block[i] = possiblePositions[9]
    if rotate == 1:
        for i in range(len(numList)):
            if numList[i] == 1:
                block[i] = possiblePositions[6]
            if numList[i] == 2:
                block[i] = possiblePositions[3]
            if numList[i] == 3:
                block[i] = possiblePositions[0]
            if numList[i] == 4:
                block[i] = possiblePositions[7]
            if numList[i] == 5:
                block[i] = possiblePositions[4]
            if numList[i] == 6:
                block[i] = possiblePositions[1]
            if numList[i] == 7:
                block[i] = possiblePositions[8]
            if numList[i] == 8:
                block[i] = possiblePositions[5]
            if numList[i] == 9:
                block[i] = possiblePositions[2] 
            if numList[i] == 10:
                block[i] = possiblePositions[10]
    if rotate == 2:
        for i in range(len(numList)):
            if numList[i] == 1:
                block[i] = possiblePositions[8]
            if numList[i] == 2:
                block[i] = possiblePositions[7]
            if numList[i] == 3:
                block[i] = possiblePositions[6]
            if numList[i] == 4:
                block[i] = possiblePositions[5]
            if numList[i] == 5:
                block[i] = possiblePositions[4]
            if numList[i] == 6:
                block[i] = possiblePositions[3]
            if numList[i] == 7:
                block[i] = possiblePositions[2]
            if numList[i] == 8:
                block[i] = possiblePositions[1]
            if numList[i] == 9:
                block[i] = possiblePositions[0]   
            if numList[i] == 10:
                block[i] = possiblePositions[11]
    if rotate == 3:
        for i in range(len(numList)):
            if numList[i] == 1:
                block[i] = possiblePositions[2]
            if numList[i] == 2:
                block[i] = possiblePositions[5]
            if numList[i] == 3:
                block[i] = possiblePositions[8]
            if numList[i] == 4:
                block[i] = possiblePositions[1]
            if numList[i] == 5:
                block[i] = possiblePositions[4]
            if numList[i] == 6:
                block[i] = possiblePositions[7]
            if numList[i] == 7:
                block[i] = possiblePositions[0]
            if numList[i] == 8:
                block[i] = possiblePositions[3]
            if numList[i] == 9:
                block[i] = possiblePositions[6] 
            if numList[i] == 10:
                block[i] = possiblePositions[12]
    return block

def collision(block_list, block):
    rectList = []
    genNewBlock = False
    if len(block_list)>1:
        for i in range(len(block_list)-1):
            block1 = block_list[i]
            for j in range(len(block1)):
                rectList.append(block1[j])
        for i in range(len(block)):
            for j in range(len(rectList)):
                if rectList[j].collidepoint(block[i].x, block[i].y+30):
                    genNewBlock = True
    return genNewBlock

def collisionX(block_list, block):
    rectList = []
    left, right = True, True
    if len(block_list)>1:
        for i in range(len(block_list)-1):
            block1 = block_list[i]
            for j in range(len(block1)):
                rectList.append(block1[j])
        for i in range(len(block)):
            for j in range(len(rectList)):
                if rectList[j].collidepoint(block[i].x-30, block[i].y):
                    left = False
    if len(block_list)>1:
        for i in range(len(block_list)-1):
            block1 = block_list[i]
            for j in range(len(block1)):
                rectList.append(block1[j])
        for i in range(len(block)):
            for j in range(len(rectList)):
                if rectList[j].collidepoint(block[i].x+30, block[i].y):
                    right = False    
    return left, right

def win(block_list):
    yList = []
    rectList = []
    for i in range(len(block_list)):
        block1 = block_list[i]
        for j in range(len(block1)):
            yList.append(block1[j].y)
            rectList.append(block1[j])
    for i in yList:
        num = yList.count(i)
        if num >= 17:
            return i
    return None

def removeBlock(removeY, block_list):
    removeList = []
    insertList = []
    if removeY!=None:
        for i in range(len(block_list)):
            block2 = block_list[i]
            for j in range(len(block2)):
                if block2[j].y == removeY:
                    insertList = [i,j]
                    removeList.append(insertList)
                    #use targeting to remove rects. 

    print(removeList)
        
#-------------------MAIN PROGRAM loop 

def main():
    block, colorList, block_list, color, heightIndex, getNewRect, rotate, y, x, rectList, left, right, removeY = 0, [], [], 0, 0, True, 0, 0, w/2, [], True, True, None
    while(True):
        
        if getNewRect == True:
            rotate = 0
            block, color, numList = selectRandomBlock(w/2, 0)
            colorList.append(color)
            y, x = 0, w/2
            block_list.append(block)
            if color == YELLOW or color == ORANGE or color == RED or color == LTGREEN or color == BLUE:
                width = 60
            elif color == LTBLUE:
                width = 120
            else:
                width = 90            
            getNewRect = False        
        
        for event in pygame.event.get():
            if (event.type==pygame.QUIT or (event.type==pygame.KEYDOWN and event.key==pygame.K_ESCAPE)):
                pygame.quit()
                sys.exit()   
            if getNewRect == False:
                if event.type == pygame.KEYDOWN and event.key == pygame.K_UP:
                    rotate+=1
                    
                           
            #PLACE SAFETY NETS SO THAT X MOVEMENTS DO NOT WORK IF THEY WILL RESULT IN BLOCK COLLISIONS. 
            if getNewRect == False:
                if event.type == pygame.KEYDOWN and event.key == pygame.K_LEFT:
                    if block[widthIndex].x>0 and left == True:
                        x-=30
                        for i in range(len(block)):
                                block[i].x-=30
                                    
                                    
            if getNewRect == False:
                if event.type == pygame.KEYDOWN and event.key == pygame.K_RIGHT:
                    if block[widthIndex].x+width<w and right == True:
                        x+=30
                        for i in range(len(block)):
                                block[i].x+=30
    
        if getNewRect == False: 
            removeY = win(block_list)
            removeBlock(removeY, block_list)
            if rotate>3:
                rotate = 0
            referenceList = generateReferenceList(x, y)            
            y = moveX_and_Y(y, heightIndex, block)
            moveBlockDown(block, heightIndex)
            heightIndex, widthIndex = calculateIndeces(block)
            block = rotateBlock(block, color, rotate, numList, referenceList)
            collision(block_list, block)
            left, right = collisionX(block_list, block)
            if collision(block_list, block) == True:
                getNewRect = True            
            if block[heightIndex].y+30>=h:
                getNewRect = True   
                
        surface.fill(WHITE)
        
        for i in range(len(block_list)):
            drawnBlock = block_list[i]
            for j in range(len(drawnBlock)):
                pygame.draw.rect(surface, colorList[i], drawnBlock[j], 0), pygame.draw.rect(surface, BLACK, (drawnBlock[j].x-2, drawnBlock[j].y-2, drawnBlock[j].w+4, drawnBlock[j].h+4), 4)
        
        pygame.display.update()
        
        clock.tick(120)                    
      
main()