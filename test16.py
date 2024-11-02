#Ari Kralios
import pygame, sys, math, random

#initialize game engine
pygame.init()

# open and set a window size 
w = 651
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

#add clock to control timing for animation
clock = pygame.time.Clock()


##################   FUNCTIONS   ####################################

def generateNumList():
    number_of_rects = random.randint(1, 9)
    numList = []
    for i in range(number_of_rects):
        num = random.randint(1, 9)
        numList.append(num)
    numList.sort()
    return numList
 
def generateRect(x, y, numList):
    block = []
    for i in range(len(numList)):
        if numList[i] == 1:
            block.append(pygame.Rect(x-46, y, 30, 30))
        elif numList[i] == 2:
            block.append(pygame.Rect(x-15, y, 30, 30))
        elif numList[i] == 3:
            block.append(pygame.Rect(x+16, y, 30, 30))
        elif numList[i] == 4:
            block.append(pygame.Rect(x-46, y+31, 30, 30))
        elif numList[i] == 5:
            block.append(pygame.Rect(x-15, y+31, 30, 30))
        elif numList[i] == 6:
            block.append(pygame.Rect(x+16, y+31, 30, 30))
        elif numList[i] == 7:
            block.append(pygame.Rect(x-46, y+62, 30, 30))
        elif numList[i] == 8:
            block.append(pygame.Rect(x-15, y+62, 30, 30))
        else:
            block.append(pygame.Rect(x+16, y+62, 30, 30))
    return block

#FIX DETERMINATION
def determineBlockValidity(numList):
    validityList, valid = [], False 
    if 2 in numList:
        if 5 in numList:
            if 1 in numList or 3 in numList:
                validityList.append(1)
            else:
                validityList.append(0)
        else: 
            validityList.append(0)
    if 3 in numList:
        if 6 in numList or 2 in numList:
            validityList.append(1)
        else:
            validityList.append(0)
    if 1 in numList:
        if 4 in numList or 2 in numList:
            validityList.append(1)
        else:
            validityList.append(0)
    if 5 in numList:
        if 2 in numList or 4 in numList or 6 in numList or 8 in numList:
            validityList.append(1)
        else:
            validityList.append(0)
    if 6 in numList:
        if 5 in numList:
            if 3 in numList or 9 in numList:
                validityList.append(1)
            else:
                validityList.append(0)
        else:
            validityList.append(0)
    if 4 in numList:
        if 5 in numList:
            if 1 in numList or 7 in numList:
                validityList.append(1)
            else:
                validityList.append(0)
        else:
            validityList.append(0)
    if 8 in numList:
        if 5 in numList:
            if 9 in numList or 7 in numList:
                validityList.append(1)
            else:
                validityList.append(0)
        else:
            validityList.append(0)
    if 9 in numList:
        if 6 in numList or 8 in numList:
            validityList.append(1)
        else:
            validityList.append(0)
    if 7 in numList:
        if 8 in numList or 4 in numList:
            validityList.append(1)
        else:
            validityList.append(0)
    if 0 not in validityList:
        valid = True
    return valid  
        
def describePossibleBlockPositions(x, y):
    possiblePositions = [pygame.Rect(x-45, y, 30, 30), pygame.Rect(x-15, y, 30, 30), pygame.Rect(x+15, y, 30, 30), pygame.Rect(x-45, y+30, 30, 30), pygame.Rect(x-15, y+30, 30, 30), pygame.Rect(x+15, y+30, 30, 30), pygame.Rect(x-45, y+60, 30, 30), pygame.Rect(x-15, y+60, 30, 30), pygame.Rect(x+15, y+60, 30, 30)]     
    return possiblePositions
        
def generateBlock(x, y):
    block, valid = [], False
    while(not(valid)):
        numList = generateNumList()        
        block = generateRect(x, y, numList)
        possiblePositions = describePossibleBlockPositions(x, y)
        valid = determineBlockValidity(numList)
    return block, numList
    
def calculateBlockWidth_and_BlockHeight(block):
    block_height, height_high, height_low, block_width, width_high, width_low = 0, 0, h, 0, 0, w
    block_height_index, block_width_index = 0, 0
    yList, xList = [], []
    for i in range(len(block)):
        if block[i].x>width_high:
            width_high = block[i].x
        if block[i].x<width_low:
            width_low = block[i].x
            block_width_index = i
        if block[i].y>height_high:
            height_high = block[i].y
        if block[i].y<height_low:
            height_low = block[i].y
            block_height_index = i
    block_height = height_high-height_low+30        
    block_width = width_high-width_low+30
    return block_width, block_height, block_height_index, block_width_index
    

#def computeCollision(block_height_index, block):
#    rectList = []
#    for i in range(len(block_list)):
#        block1[i] = block_list[i]
#        for j in range(len(block1)):
#            drawnBlock = block_list[i]
#            
#    YList = []
#    for i in range(len(block)):
#        if block[i].y == block[block_height_index].y:
#            YList.append(block[i])
#    for i in range(len(YList)):
#        if 
    

    
def moveBlock(block, keys, block_width, block_height, block_height_index, block_width_index):
    if block[block_height_index].y+block_height<h:
        for i in range(len(block)):
            block[i].y+=1
    return block

def moveX_and_Y(x, y, keys, block, block_width, block_height, block_height_index, block_width_index):
    if block[block_height_index].y+block_height<h:
        y+=1
    return x, y

def rotateBlock(block, rotate, numList, possiblePositions):
    rotateVertically = [0, 1, 2, 3, 4, 5, 6, 7, 8]
    rotateHorizontally = [6, 3, 0, 7, 4, 1, 8, 5, 2]
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
    return block

def randomColor():
    done = False
    while(not(done)):
        color = (random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))
        if color[0]!=color[1]!=color[2]:
            done = True
    return color

def message(words, font, size,x,y,color,bg=None):
    font = pygame.font.SysFont(font,size,True,False)
    textImage = font.render(words,True,color, bg)
    textBounds =textImage.get_rect()  #bounding box of the text image
    textBounds.center=(x,y) #center text within the bounding box
    surface.blit(textImage,textBounds)    #put on screen
    return textBounds #returns rect for collision detection

#-------------------MAIN PROGRAM loop 

def main():
    
    moveLeft, moveRight, block_list, generateNewBlock, rotate, colorList, gameOver, playAgainMessage, quitMessage, mouseOverPlayAgain, mouseOverQuit = True, True, [], True, 0, [], False, message('   PLAY AGAIN   ','Arial', 40, w/2, h/2+30, BLACK, WHITE), message('      QUIT      ', 'Arial', 40, w/2, h/2+90, BLACK, WHITE), False, False
    while(True):
        
        if gameOver == False:
            
            if generateNewBlock == True:
                                
                block, numList = generateBlock(w/2, 0)
                x, y = w/2, 0
                block_list.append(block)
                color = randomColor()
                colorList.append(color)
                generateNewBlock=False
                moveLeft, moveRight = True, True
        
            keys = pygame.key.get_pressed()
            
            for event in pygame.event.get():
                if (event.type==pygame.QUIT or (event.type==pygame.KEYDOWN and event.key==pygame.K_ESCAPE)):
                    pygame.quit()
                    sys.exit()     
                
                if generateNewBlock == False:
                    
                    if keys[pygame.K_DOWN]:
                        rotate+=1

            if generateNewBlock == False:   
                                
                block_width, block_height, block_height_index, block_width_index = calculateBlockWidth_and_BlockHeight(block)
                
                if rotate >= 4:
                    rotate = 0
                
                x, y = moveX_and_Y(x, y, keys, block, block_width, block_height, block_height_index, block_width_index)                 
                
                possiblePositions = describePossibleBlockPositions(x, y)
                
                block = moveBlock(block, keys, block_width, block_height, block_height_index, block_width_index)
                                
                block = rotateBlock(block, rotate, numList, possiblePositions)       
                
                if block[block_height_index].y+block_height>=h:        
                    generateNewBlock = True        
        else:
            for event in pygame.event.get():
                if (event.type==pygame.QUIT or (event.type==pygame.KEYDOWN and event.key==pygame.K_ESCAPE)) or (quitMessage.collidepoint(pygame.mouse.get_pos()) and event.type == pygame.MOUSEBUTTONDOWN and event.button == 1):
                    pygame.quit()
                    sys.exit()      
            if playAgainMessage.collidepoint(pygame.mouse.get_pos()):
                mouseOverPlayAgain = True
                if event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
                    block_list, generateNewBlock, rotate, colorList, gameOver, playAgainMessage, quitMessage, mouseOverPlayAgain, mouseOverQuit = [], True, 0, [], False, message('   PLAY AGAIN   ','Arial', 40, w/2, h/2+30, BLACK, WHITE), message('      QUIT      ', 'Arial', 40, w/2, h/2+90, BLACK, WHITE), False, False
            else:
                mouseOverPlayAgain = False
            if quitMessage.collidepoint(pygame.mouse.get_pos()):
                mouseOverQuit = True
            else:
                mouseOverQuit = False
        print(generateNewBlock)
        surface.fill(WHITE)
        
        for i in range(len(block_list)):
            for j in range(len(block_list[i])):
                drawBlock = block_list[i]
                pygame.draw.rect(surface, colorList[i], drawBlock[j], 0), pygame.draw.rect(surface, BLACK, (drawBlock[j].x-2, drawBlock[j].y-2, drawBlock[j].w+4, drawBlock[j].h+4), 4)
        if gameOver == True:
            pygame.draw.rect(surface, WHITE, (w/2-w/4, h/2-h/4, w/4*2, h/4*2), 0), pygame.draw.rect(surface, BLACK, (w/2-w/4, h/2-h/4, w/4*2, h/4*2), 5), message('   GAME OVER   ', 'Arial', 55, w/2, h/2-80, BLACK, None)
            if mouseOverPlayAgain == False:
                message('   PLAY AGAIN   ','Arial', 40, w/2, h/2+30, WHITE, BLACK)
            else:
                message('   PLAY AGAIN   ','Arial', 45, w/2, h/2+30, WHITE, BLACK)
            if mouseOverQuit == False:
                message('      QUIT      ', 'Arial', 40, w/2, h/2+90, WHITE, BLACK)
            else:
                message('      QUIT      ', 'Arial', 45, w/2, h/2+90, WHITE, BLACK)
        
        pygame.display.update()
        
        clock.tick(100)                    
      
main()