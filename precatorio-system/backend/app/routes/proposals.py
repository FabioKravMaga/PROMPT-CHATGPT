from fastapi import APIRouter
from pydantic import BaseModel
from typing import List

router = APIRouter()

class Proposal(BaseModel):
    id: int
    lead_id: int
    amount: float
    discount_rate: float
    status: str = "pending"

PROPOSALS_DB: List[Proposal] = []

@router.post("/", response_model=Proposal)
async def create_proposal(proposal: Proposal):
    PROPOSALS_DB.append(proposal)
    return proposal

@router.get("/", response_model=List[Proposal])
async def list_proposals():
    return PROPOSALS_DB
